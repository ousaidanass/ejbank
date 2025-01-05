package com.ejbank.dto.account;

import java.math.BigDecimal;

public class AccountResponseDto  {
    private final long id;
    private final String type;
    private final BigDecimal amount;

    public AccountResponseDto(long accountId, String accountType, BigDecimal balance) {
        this.id = accountId;
        this.type = accountType;
        this.amount = balance;
    }

    public long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
