package com.ejbank.dto.account;

import java.math.BigDecimal;

public class AccountAdvisorResponseDto {
    private final long id;
    private final String user;
    private final String type;
    private final BigDecimal amount;

    public AccountAdvisorResponseDto(long accountId, String user, String accountType, BigDecimal balance) {
        this.id = accountId;
        this.user = user;
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

    public String getUser() {
            return user;
        }
}
