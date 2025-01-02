package com.ejbank.bean;

import com.ejbank.dto.transaction.TransactionsDto;
import com.ejbank.exception.TraitementException;

import javax.ejb.Local;

@Local
public interface TransactionBean {
    static final int PAGINATION = 5;
    TransactionsDto getTransactionList(long userId, long accountId, int offset) throws TraitementException;
}
