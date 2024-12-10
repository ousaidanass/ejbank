package com.ejbank.dto;

import java.math.BigDecimal;

public class AccountsResponceDto {
    private int accountId;
    private String accountType;
    private BigDecimal balance;
    private String error;

    public AccountsResponceDto(int accountId, String accountType, BigDecimal balance) {
        accountId = accountId;
        accountType = accountType;
        balance = balance;
        error = null;
    }

    public AccountsResponceDto(String error) {
        this.error = error;
    }

    public int getAccountId() {
        return accountId;
    }

    public String getAccountType() {
        return accountType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getError() {
        return error;
    }
}
