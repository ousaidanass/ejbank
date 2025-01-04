package com.ejbank.bean.impl;

import com.ejbank.bean.BeanRequestAssertion;
import com.ejbank.bean.TransactionBean;
import com.ejbank.dto.TransactionDispatchDto;
import com.ejbank.dto.transaction.*;
import com.ejbank.exception.ErrorIdentifier;
import com.ejbank.exception.TraitementException;
import com.ejbank.model.EjbankAccount;
import com.ejbank.model.EjbankTransaction;
import com.ejbank.model.EjbankUser;

import javax.ejb.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public TransactionsDto getTransactionList(long userId, long accountId, int offset) throws TraitementException {
        var user = em.find(EjbankUser.class, userId);

        var invalidProcess = beanRequestAssertion.isInvalidUserAccount(accountId, userId, user);
        if (invalidProcess.isPresent()) {
            throw new TraitementException(invalidProcess.get());
        }

        var query = em.createQuery(
                "SELECT t FROM EjbankTransaction t " +
                        "WHERE (t.accountFrom.id = " + accountId + " " +
                        "OR t.accountTo.id = " + accountId + " ) " +
                        "ORDER BY t.date DESC"
        );
        query.setFirstResult(offset);
        query.setMaxResults(PAGINATION);
        List<EjbankTransaction> transactions = query.getResultList();

        query = em.createQuery("SELECT COUNT(t) FROM EjbankTransaction t WHERE t.accountFrom.id = " + accountId);
        var total = (int) query.getSingleResult();

        var transactionDtos = new ArrayList<TransactionDispatchDto>();
        for (EjbankTransaction transaction : transactions) {
            var state = transaction.getApplied() ? TransactionState.APPLIED : beanRequestAssertion.isAdvisor(user) ? TransactionState.TO_APPROVE : TransactionState.WAITING_APPROVE;
            var parsedDto = transaction.getComment() == null || transaction.getComment().isEmpty() ?
            new TransactionDto(
                    transaction.getId(),
                    transaction.getDate().toString(),
                    transaction.getAccountFrom().getAccountType().getName(),
                    transaction.getAccountTo().getAccountType().getName(),
                    transaction.getAccountTo().getCustomer().getFirstname(),
                    transaction.getAmount(),
                    transaction.getAccountFrom().getCustomer().getFirstname(),
                    state
            ) :
                    new CommentedTransactionDto(
                            transaction.getId(),
                            transaction.getDate().toString(),
                            transaction.getAccountFrom().getAccountType().getName(),
                            transaction.getAccountTo().getAccountType().getName(),
                            transaction.getAccountTo().getCustomer().getFirstname(),
                            transaction.getAmount(),
                            transaction.getAccountFrom().getCustomer().getFirstname(),
                            transaction.getComment(),
                            state
                    );
            transactionDtos.add(parsedDto);
        }
        return new TransactionsDto(total, transactionDtos);
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
}
