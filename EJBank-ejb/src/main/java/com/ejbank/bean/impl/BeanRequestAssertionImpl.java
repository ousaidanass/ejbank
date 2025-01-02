package com.ejbank.bean.impl;

import com.ejbank.bean.BeanRequestAssertion;
import com.ejbank.exception.ErrorIdentifier;
import com.ejbank.model.EjbankAccount;
import com.ejbank.model.EjbankAdvisor;
import com.ejbank.model.EjbankCustomer;
import com.ejbank.model.EjbankUser;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Stateless
public class BeanRequestAssertionImpl implements BeanRequestAssertion {
    @PersistenceContext(name = "EJBankDS")
    private EntityManager em;

    public boolean isAdvisor(EjbankUser user) {
        return user instanceof EjbankAdvisor;
    }

    public boolean isCustomer(EjbankUser user) {
        return user instanceof EjbankCustomer;
    }
    public Optional<List<EjbankCustomer>> getUserCustomers(EjbankUser user, long id) {
        if (isAdvisor(user)) {
            var advisor = em.find(EjbankAdvisor.class, id);
            return Optional.of(new ArrayList<>(advisor.getEjbankCustomers()));
        } else if (isCustomer(user)) {
            return Optional.of(List.of(em.find(EjbankCustomer.class, id)));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<ErrorIdentifier> isInvalidUserAccount(long accountId, long userId, EjbankUser user) {
        var customers = getUserCustomers(user, userId).orElse(null);
        EjbankAccount account;
        EjbankCustomer customer;

        if (customers == null) {
            return Optional.of(ErrorIdentifier.USER_NOT_FOUND);
        }
        if (isAdvisor(user)) {
            account = customers.stream()
                    .map(EjbankCustomer::getAccounts)
                    .flatMap(Collection::stream)
                    .filter(acc -> Objects.equals(acc.getId(), accountId))
                    .findFirst()
                    .orElse(null);
            if (account == null) {
                return Optional.of(ErrorIdentifier.ACCOUNT_NOT_ASSIGNED_TO_ADVISOR);
            }
        } else {
            customer = customers.get(0);
            account = customer.getAccounts().stream()
                    .filter(acc -> acc.getId() == accountId)
                    .findFirst()
                    .orElse(null);
            if (account == null) {
                return Optional.of(ErrorIdentifier.ACCOUNT_NOT_ASSIGNED_TO_CUSTOMER);
            }
        }
        return Optional.empty();
    }
}
