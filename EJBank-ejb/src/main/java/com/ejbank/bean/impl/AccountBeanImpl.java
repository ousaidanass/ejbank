package com.ejbank.bean.impl;

import com.ejbank.bean.AccountBeanLocal;
import com.ejbank.dto.Account;
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
        var user = em.createQuery("SELECT " +
                "account.id as id, " +
                "CONCAT(user.firstname, ' ', user.lastname, '(', user.login, ')') as user, " +
                "accountType.name as typee, " +
                "account.balance as amount, " +
                "COUNT(transaction.applied) as validation " +
                "FROM EjbankUser user "
        + "JOIN EjbankCustomer customer ON user.id = customer.id "
        + "JOIN EjbankAccount account ON user.id = account.id "
        + "JOIN EjbankTransaction transaction ON account.id = transaction.id "
        + "JOIN EjbankAccountType accountType ON account.id = transaction.accountType.id "
        + "WHERE customer.ejbankAdvisor.id = :id " +
                "GROUP BY account.id, user.firstname, user.lastname, user.login, accountType.name, account.balance", Account.class);
        var result = user.getResultList();
        return new AccountsAttachedResponseDto(result);
    }

    @Override
    public AccountsResponceDto getAccount(long id) throws TraitementException {
        em.find(EjbankUser.class, id);
        return null;
    }

}
