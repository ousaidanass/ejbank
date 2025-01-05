package com.ejbank.bean;

import com.ejbank.dto.account.AccountAdvisorResponseDto;
import com.ejbank.dto.account.AccountDetailResponseDto;
import com.ejbank.dto.account.AccountResponseDto;
import com.ejbank.dto.account.AccountsAttachedResponseDto;
import com.ejbank.exception.TraitementException;

import javax.ejb.Local;
import java.util.List;

@Local
public interface AccountBeanLocal {
    AccountsAttachedResponseDto getAccountsAttached(long id) throws TraitementException;
    List<AccountResponseDto> getAccounts(long id) throws TraitementException;
    List<AccountAdvisorResponseDto> getAllAccounts(long id) throws TraitementException;
    AccountDetailResponseDto getAccountDetail(long accountId, long userId) throws TraitementException;
}

