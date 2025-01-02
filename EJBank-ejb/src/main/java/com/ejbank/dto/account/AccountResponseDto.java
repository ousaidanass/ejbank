package com.ejbank.dto.account;

import com.ejbank.dto.AccountDispatchDto;

import java.math.BigDecimal;

public class AccountResponseDto implements AccountDispatchDto {
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
