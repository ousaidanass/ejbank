package com.ejbank.dto.transaction;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TransactionsResponseDto<T> {
    private Long total;
    private List<T> transactions;
    private final String error;

    @Override
    public String toString() {
        return "{ \"total\":" +
                total +
                ",\"transactions\":" +
                transactions.stream().map(T::toString).collect(Collectors.joining(",", "[", "]")) +
                ",\"error\":"+error+"}";
    }

    public TransactionsResponseDto(Long total, List<T> transactions) {
        this.total = total;
        this.transactions = Objects.requireNonNull(transactions);
        this.error = null;
    }

    public TransactionsResponseDto(String error) {
        this.error = error;
    }

    public Long getTotal() {
        return total;
    }

    public List<T> getTransactions() {
        return transactions;
    }
}
