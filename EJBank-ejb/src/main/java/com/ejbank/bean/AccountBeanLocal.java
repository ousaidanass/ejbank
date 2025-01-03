package com.ejbank.bean;

import com.ejbank.dto.AccountDispatchDto;
import com.ejbank.dto.account.AccountDetailResponseDto;
import com.ejbank.dto.account.AccountsAttachedResponseDto;
import com.ejbank.exception.TraitementException;

import javax.ejb.Local;

@Local
public interface AccountBeanLocal {
    AccountsAttachedResponseDto getAccountsAttached(long id) throws TraitementException;
    AccountDispatchDto getAccounts(long id) throws TraitementException;

    AccountDispatchDto getAllAccounts(long id) throws TraitementException;

    AccountDetailResponseDto getAccountDetail(long accountId, long userId) throws TraitementException;
}

