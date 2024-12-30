package com.ejbank.dto;

import java.math.BigDecimal;

public class AccountResponseDto {
    private long accountId;
    private String accountType;
    private BigDecimal balance;

    public AccountResponseDto(long accountId, String accountType, BigDecimal balance) {
        this.accountId = accountId;
        this.accountType = accountType;
        this.balance = balance;
    }

    public long getAccountId() {
        return accountId;
    }

    public String getAccountType() {
        return accountType;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
