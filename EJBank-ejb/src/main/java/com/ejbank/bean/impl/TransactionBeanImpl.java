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

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Stateless
@LocalBean
public class TransactionBeanImpl implements TransactionBean {
    @EJB
    private BeanRequestAssertion beanRequestAssertion;

    @PersistenceContext(name = "EJBankDS")
    private EntityManager em;



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
        if (requestDto.getSource() == requestDto.getDestination()) {
            throw new TraitementException(ErrorIdentifier.TRANSACTION_TO_SAME_SOURCE);
        }
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
}
