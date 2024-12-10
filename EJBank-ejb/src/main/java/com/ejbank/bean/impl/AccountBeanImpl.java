package com.ejbank.bean.impl;

import com.ejbank.bean.AccountBeanLocal;
import com.ejbank.dto.AccountsAttachedResponseDto;
import com.ejbank.dto.UserResponseDto;
import com.ejbank.exception.TraitementException;
import com.ejbank.model.EjbankUser;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class AccountBeanImpl implements AccountBeanLocal {
    @PersistenceContext(name = "EJBankDS")
    private EntityManager em;

    @Override
    public AccountsAttachedResponseDto getAccountsAttached(long id) throws TraitementException {

        return null;
    }
}
