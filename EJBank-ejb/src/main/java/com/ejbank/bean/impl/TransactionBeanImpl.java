package com.ejbank.bean.impl;

import com.ejbank.bean.BeanRequestAssertion;
import com.ejbank.bean.TransactionBean;
import com.ejbank.dto.transaction.*;
import com.ejbank.exception.ErrorIdentifier;
import com.ejbank.exception.TraitementException;
import com.ejbank.model.EjbankAccount;
import com.ejbank.model.EjbankAdvisor;
import com.ejbank.model.EjbankTransaction;
import com.ejbank.model.EjbankUser;

import javax.ejb.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class TransactionBeanImpl implements TransactionBean {
    @EJB
    private BeanRequestAssertion beanRequestAssertion;

    @PersistenceContext(name = "EJBankDS")
    private EntityManager em;

    private InitialContext ctx = new InitialContext();
    private UserTransaction tx = (UserTransaction) ctx.lookup("UserTransaction");

    public TransactionBeanImpl() throws NamingException {
    }

    /**
     * Retrieves a paginated list of transactions for a specific account.
     * Validates the user's access to the account before processing.
     *
     * @param userId The unique identifier of the user requesting transactions.
     * @param accountId The unique identifier of the account for which transactions are retrieved.
     * @param offset The starting point for pagination.
     *
     * @return A {@link TransactionsResponseDto} containing the total count of transactions and a list of transaction details.
     *
     * @throws TraitementException If the user does not have access to the specified account or any other processing error occurs.
     */
    @Override
    public TransactionsResponseDto<TransactionResponseDto> getTransactionList(long userId, long accountId, int offset) throws TraitementException {
        var user = em.find(EjbankUser.class, userId);

        var invalidProcess = beanRequestAssertion.isInvalidUserAccount(accountId, userId, user);
        if (invalidProcess.isPresent()) {
            throw new TraitementException(invalidProcess.get());
        }

        var queryCount = em.createQuery(
                "SELECT count(t) FROM EjbankTransaction t " +
                        "WHERE t.accountFrom.id = :accountId ORDER BY t.date DESC"
        );
        queryCount.setParameter("accountId", accountId);
        var total = (Long) queryCount.getSingleResult();

        var query = em.createQuery(
                "SELECT t FROM EjbankTransaction t " +
                        "WHERE t.accountFrom.id = :accountId ORDER BY t.date DESC"
        );
        query.setParameter("accountId", accountId);
        query.getMaxResults();
        query.setFirstResult(offset);
        query.setMaxResults(PAGINATION);
        List<EjbankTransaction> transactions = query.getResultList();

        var mapped = transactions.stream().map( t -> {
                    TransactionState state;
                    if (Objects.requireNonNull(t.getApplied()) == Boolean.FALSE) {
                        state = (user instanceof EjbankAdvisor) ?
                                TransactionState.TO_APPROVE : TransactionState.WAITING_APPROVE;
                    } else {
                        state = TransactionState.APPLIED;
                    }
                    return new TransactionResponseDto(
                            t.getId(),
                            t.getDate().toString(),
                            t.getAccountFrom().getAccountType().getName(),
                            t.getAccountTo().getAccountType().getName(),
                            t.getAccountTo().getCustomer().getFirstname(),
                            t.getAmount(),
                            t.getAccountFrom().getCustomer().getFirstname(),
                            t.getComment(),
                            state.toString()
                    );
                }
        ).toList();
        return new TransactionsResponseDto<>(total, mapped);
    }

    @Override
    public TransactionPreviewResponseDto previewTransaction(TransactionPreviewRequestDto requestDto) throws TraitementException {
        var source = em.find(EjbankAccount.class, requestDto.getSource());
        var destination = em.find(EjbankAccount.class, requestDto.getDestination());
        if (source == null) {
            throw new TraitementException(ErrorIdentifier.SOURCE_ACCOUNT_NOT_FOUND);
        }
        if (destination == null) {
            throw new TraitementException(ErrorIdentifier.DESTINATION_ACCOUNT_NOT_FOUND);
        }
        var before = source.getBalance();
        var after = before.subtract(requestDto.getAmount());
        var result = after.compareTo(BigDecimal.valueOf(-source.getAccountType().getOverdraft())) >= 0;
        String message = result ? "Transaction accepter" : "Vous ne disposez pas d'un solde suffisant";
        return new TransactionPreviewResponseDto(result, before, after, message);
    }

    private boolean isTransactionValide(EjbankTransaction transaction) {
        var before = transaction.getAccountFrom().getBalance();
        var after = before.subtract(transaction.getAmount());
        return after.compareTo(BigDecimal.valueOf(-transaction.getAccountFrom().getAccountType().getOverdraft())) >= 0;
    }

    private boolean isTransactionValide(EjbankAccount account, BigDecimal amount) {
        var before = account.getBalance()
                .add(BigDecimal.valueOf(account.getAccountType().getOverdraft()));
        System.err.println(before + " " + amount);
        return before.compareTo(amount) >= 0;
    }


    @Override
    public TransactionValidationResponseDto validateTransaction(TransactionValidationRequestDto requestDto) throws TraitementException {
        var transaction = em.find(EjbankTransaction.class, requestDto.getTransaction());
        if (transaction == null) {
            throw new TraitementException(ErrorIdentifier.TRANSACTION_NOT_FOUND);
        }
        if (!transaction.getAuthor().getId().equals(requestDto.getAuthor())) {
            throw new TraitementException(ErrorIdentifier.AUTHOR_IS_NOT_CORRECT);
        }
        if (transaction.getApplied()) {
            return new TransactionValidationResponseDto(false, "Transaction déjà valider");
        }
        if (requestDto.getApprove()) {
            if (!isTransactionValide(transaction)) {
                throw new TraitementException(ErrorIdentifier.TRANSACTION_REFUSED);
            }
            try {
                tx.begin();
                transaction.setApplied(true);
                transaction.getAccountFrom().setBalance(transaction.getAccountFrom().getBalance().subtract(transaction.getAmount()));
                em.merge(transaction.getAccountFrom());
                transaction.getAccountTo().setBalance(transaction.getAccountTo().getBalance().add(transaction.getAmount()));
                em.merge(transaction.getAccountTo());
                em.merge(transaction);
                tx.commit();
                return new TransactionValidationResponseDto(true, "Transaction valider");
            } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException |
                     HeuristicRollbackException e) {
                throw new TraitementException(ErrorIdentifier.TECHNICAL_ERROR);
            } catch (Exception e) {
                try {
                    tx.rollback();
                    throw new TraitementException(ErrorIdentifier.TECHNICAL_ERROR);
                } catch (SystemException ex) {
                    throw new TraitementException(ErrorIdentifier.TECHNICAL_ERROR);
                }
            }
        } else {
            em.remove(transaction);
            return new TransactionValidationResponseDto(false, "Transaction supprimer");
        }
    }

    /**
     * Retrieves the count of pending transactions that require approval.
     * For advisors, it counts transactions of their managed customers. For customers, it counts their own pending transactions.
     *
     * @param userId The unique identifier of the user (advisor or customer)
     *               .
     * @return A string representing the number of pending transactions.
     *
     * @throws TraitementException If the user is not found or other validation fails.
     */
    @Override
    public String getPendingTransactionCount(long userId) throws TraitementException {
        var user = em.find(EjbankUser.class, userId);
        var isAdvisor = user instanceof EjbankAdvisor;

        var query = em.createQuery(
                "SELECT count(t.id) " +
                        "from EjbankCustomer c " +
                        "inner join EjbankAccount a on c.id=a.ejbankCustomer.id " +
                        "inner join EjbankTransaction t ON a.id=t.accountFrom.id " +
                        "WHERE "+ (isAdvisor?"c.ejbankAdvisor.id":"c.id")+" = :userId " +
                        "and t.applied=false and abs(t.amount) >= 1000"
        );
        query.setParameter("userId", userId);
        return query.getSingleResult().toString();
    }

    private long getNextId() {
        var maxId = em.createQuery(
                "SELECT COALESCE(MAX(e.id), 0) FROM EjbankTransaction e", Long.class
        ).getSingleResult();
        return maxId + 1;
    }

    /**
     * Applies a new transaction between accounts if it passes all validation checks.
     * The transaction is created but not immediately applied until later approval.
     *
     * @param request A {@link TransactionApplyDto} containing transaction details such as source, destination, and amount.
     *
     * @return A {@link TransactionValidationResponseDto} indicating if the transaction was successfully created and is valid.
     *
     * @throws TraitementException If the user does not have access to the source account, or if the transaction is invalid.
     */
    @Override
    @Transactional
    public TransactionValidationResponseDto applyTransaction(TransactionApplyDto request) throws TraitementException {
        var user = em.find(EjbankUser.class, request.getAuthor());
        var sourceAccount = em.find(EjbankAccount.class, request.getSource());
        var destinationAccount = em.find(EjbankAccount.class, request.getDestination());

        var valid = beanRequestAssertion.isInvalidUserAccount(request.getSource(), request.getAuthor(),user);
        if(valid.isPresent()){
            throw new TraitementException(valid.get());
        }
        if(!isTransactionValide(sourceAccount,request.getAmount())){
            throw new TraitementException(ErrorIdentifier.TRANSACTION_REFUSED);
        }

        var transaction = new EjbankTransaction();
        transaction.setId(getNextId());
        transaction.setAccountFrom(sourceAccount);
        transaction.setAccountTo(destinationAccount);
        transaction.setAmount(request.getAmount());
        transaction.setAuthor(user);
        transaction.setDate(new Date(System.currentTimeMillis()));
        transaction.setApplied(false);
        em.persist(transaction);

        return new TransactionValidationResponseDto(true, "transaction valide");
    }
}
