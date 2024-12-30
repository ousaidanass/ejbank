package com.ejbank.bean;

import com.ejbank.dto.AccountDispatchDto;
import com.ejbank.exception.TraitementException;

import javax.ejb.Local;

@Local
public interface AccountBeanLocal {
    AccountDispatchDto getAccountsAttached(long id) throws TraitementException;
    AccountDispatchDto getAccounts(long id) throws TraitementException;

    AccountDispatchDto getAllAccounts(long id) throws TraitementException;
}
