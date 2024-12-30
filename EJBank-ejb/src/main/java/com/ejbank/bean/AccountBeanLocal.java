package com.ejbank.bean;

import com.ejbank.dto.AccountsAttachedResponseDto;
import com.ejbank.dto.AccountsResponceDto;
import com.ejbank.exception.TraitementException;

import javax.ejb.Local;

@Local
public interface AccountBeanLocal {
    AccountsAttachedResponseDto getAccountsAttached(long id) throws TraitementException;
    AccountsResponceDto getAccounts(long id) throws TraitementException;
}
