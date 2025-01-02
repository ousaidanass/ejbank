package com.ejbank.dto.transaction;

import com.ejbank.dto.TransactionDispatchDto;

import java.util.List;
import java.util.Objects;

public class TransactionsDto {
    private int total;
    private List<TransactionDispatchDto> transactions;
    private final String error;

    public TransactionsDto(int total, List<TransactionDispatchDto> transactions) {
        this.total = total;
        this.transactions = Objects.requireNonNull(transactions);
        this.error = null;
    }

    public TransactionsDto(String error) {
        this.error = error;
    }
}
