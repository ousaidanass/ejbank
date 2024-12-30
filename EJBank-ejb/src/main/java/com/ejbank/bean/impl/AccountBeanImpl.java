package com.ejbank.bean.impl;

import com.ejbank.bean.AccountBeanLocal;
import com.ejbank.bean.BeanRequestAssertion;
import com.ejbank.dto.Account;
import com.ejbank.dto.AccountResponseDto;
import com.ejbank.dto.AccountsAttachedResponseDto;
import com.ejbank.dto.AccountsResponceDto;
import com.ejbank.exception.ErrorIdentifier;
import com.ejbank.exception.TraitementException;
import com.ejbank.model.EjbankUser;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;

@Stateless
@LocalBean
public class AccountBeanImpl implements AccountBeanLocal {
    @PersistenceContext(name = "EJBankDS")
    private EntityManager em;

    @EJB
    private BeanRequestAssertion beanRequestAssertion;

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
    public AccountsResponceDto getAccounts(long id) throws TraitementException {
        var user = em.find(EjbankUser.class, id);
        if (beanRequestAssertion.isAdvisor(user)) {
            throw new TraitementException(ErrorIdentifier.USER_IS_NOT_A_CUSTOMER);
        }
        var accounts = new ArrayList<AccountResponseDto>();
        var customers = beanRequestAssertion.getUserCustomers(user, id).orElse(null);
        if (customers == null) {
            throw new TraitementException(ErrorIdentifier.USER_NOT_FOUND);
        }
        for (var customer : customers) {
            customer.getAccounts().forEach(account -> {
                accounts.add(new AccountResponseDto(
                        account.getId(),
                        account.getAccountType().getName(),
                        account.getBalance()
                ));
            });
        }
        return new AccountsResponceDto(accounts);
    }

}
