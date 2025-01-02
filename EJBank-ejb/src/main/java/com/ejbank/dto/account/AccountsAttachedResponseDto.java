package com.ejbank.dto.account;


import com.ejbank.dto.AccountDispatchDto;

import java.util.List;

public class AccountsAttachedResponseDto implements AccountDispatchDto {
    private List<AccountDto> accounts;

    private String error;

    public AccountsAttachedResponseDto(List<AccountDto> accounts) {
        this.accounts = accounts;
    }

    public AccountsAttachedResponseDto(String error) {
        this.error = error;
    }

    public List<AccountDto> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountDto> accounts) {
        this.accounts = accounts;
    }


    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "AccountsAttachedResponseDto{" +
                "accounts=" + accounts +
                ", error='" + error + '\'' +
                '}';
    }
}
