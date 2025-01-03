package com.ejbank.bean.impl;

import com.ejbank.bean.AccountBeanLocal;
import com.ejbank.bean.BeanRequestAssertion;
import com.ejbank.dto.*;
import com.ejbank.dto.account.*;
import com.ejbank.exception.ErrorIdentifier;
import com.ejbank.exception.TraitementException;
import com.ejbank.model.EjbankAccount;
import com.ejbank.model.EjbankAdvisor;
import com.ejbank.model.EjbankCustomer;
import com.ejbank.model.EjbankUser;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
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
        var request = em.createQuery(
        "SELECT new com.ejbank.dto.account.AccountDto("
        + "account.id,"
        + "CONCAT(user.firstname, ' ', user.lastname, ' (', user.login, ')'), "
        + "CONCAT('Label du compte (', accountType.name, ')'), "
        + "account.balance, "
        + "SUM(CASE WHEN transaction.applied = true THEN 1 ELSE 0 END))"
        + "FROM EjbankUser user "
        + "JOIN EjbankCustomer customer ON user.id = customer.id "
        + "JOIN EjbankAccount account ON customer.id = account.ejbankCustomer.id "
        + "JOIN EjbankTransaction transaction ON account.id = transaction.accountFrom.id "
        + "JOIN EjbankAccountType accountType ON account.accountType.id = accountType.id "
        + "WHERE user.id = :id "
        + "GROUP BY account.id, user.firstname, user.lastname, user.login, accountType.name, account.balance"
        , AccountDto.class);
        request.setParameter("id", id);
        var result = request.getResultList();
        System.err.println("Accounts attached result: " + result);
        return new AccountsAttachedResponseDto(result);
    }

    @Override
    public AccountDispatchDto getAccounts(long id) throws TraitementException {
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

    @Override
    public AccountDispatchDto getAllAccounts(long id) throws TraitementException {
        var user = em.find(EjbankUser.class, id);
        return beanRequestAssertion.isAdvisor(user) ? getAccountsAttached(id) : getAccounts(id);
    }

    @Override
    public AccountDetailResponseDto getAccountDetail(long accountId, long userId) throws TraitementException {
        var customer = em.find(EjbankCustomer.class, userId);
        if (customer == null) {
            throw new TraitementException(ErrorIdentifier.CUSTOMER_NOT_FOUND);
        }
        var account = em.find(EjbankAccount.class, accountId);
        if (account == null) {
            throw new TraitementException(ErrorIdentifier.ACCOUNT_NOT_FOUND);
        }
        if (customer.getAccounts().contains(account)) {
            var advisor = customer.getEjbankAdvisor();
            var accountType = account.getAccountType();
            return new AccountDetailResponseDto(customer.getFirstname() + " " + customer.getLastname() + " (client)",
                    advisor.getFirstname() + " " + advisor.getLastname() + " (conseillé)",
                    accountType.getRate(), accountType.getRate().divide(BigDecimal.valueOf(100)).multiply(account.getBalance()),
                    account.getBalance());
        } else {
            throw new TraitementException(ErrorIdentifier.ACCOUNT_NOT_ASSIGNED_TO_CUSTOMER);
        }
    }

}
