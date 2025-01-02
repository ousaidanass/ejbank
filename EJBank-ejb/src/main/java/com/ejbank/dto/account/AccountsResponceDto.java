package com.ejbank.dto.account;

import com.ejbank.dto.AccountDispatchDto;

import java.util.List;
import java.util.Objects;

public class AccountsResponceDto implements AccountDispatchDto {
    private List<AccountResponseDto> accounts;
    private final String error;

    public AccountsResponceDto(List<AccountResponseDto> accounts) {
        this.accounts = Objects.requireNonNull(accounts);
        this.error = null;
    }

    public AccountsResponceDto(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public List<AccountResponseDto> getAccounts() {
        return accounts;
    }
}
