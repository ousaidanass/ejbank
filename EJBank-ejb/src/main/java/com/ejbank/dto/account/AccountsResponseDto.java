package com.ejbank.dto.account;

import java.util.List;
import java.util.Objects;

public class AccountsResponseDto<T> {
    private List<T> accounts;
    private final String error;

    public AccountsResponseDto(List<T> accounts) {
        this.accounts = Objects.requireNonNull(accounts);
        this.error = null;
    }

    public AccountsResponseDto(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public List<T> getAccounts() {
        return accounts;
    }
}
