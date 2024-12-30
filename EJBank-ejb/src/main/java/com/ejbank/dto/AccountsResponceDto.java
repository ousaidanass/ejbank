package com.ejbank.dto;

import java.util.List;
import java.util.Objects;

public class AccountsResponceDto implements AccountDispatchDto {
    private List<AccountResponseDtoDto> accounts;
    private final String error;

    public AccountsResponceDto(List<AccountResponseDtoDto> accounts) {
        this.accounts = Objects.requireNonNull(accounts);
        this.error = null;
    }

    public AccountsResponceDto(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public List<AccountResponseDtoDto> getAccounts() {
        return accounts;
    }
}
