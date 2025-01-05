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

/**
 * Implementation of {@link BeanRequestAssertion} providing user validation and customer-account relationship assertions.
 */
@Stateless
public class BeanRequestAssertionImpl implements BeanRequestAssertion {
    @PersistenceContext(name = "EJBankDS")
    private EntityManager em;

    /**
     * Retrieves the list of customers associated with a user.
     * If the user is an advisor, returns all customers linked to the advisor.
     * If the user is a customer, returns only the specified customer.
     *
     * @param user The user making the request.
     * @param id The identifier used to find the advisor or customer.
     *
     * @return An {@link Optional} containing a list of {@link EjbankCustomer}, or empty if the user is not found.
     */
    public Optional<List<EjbankCustomer>> getUserCustomers(EjbankUser user, long id) {
        var adv = em.find(EjbankAdvisor.class, user.getId());
        var ctm = em.find(EjbankCustomer.class, user.getId());
        if (adv != null) {
            var advisor = em.find(EjbankAdvisor.class, id);
            return Optional.of(new ArrayList<>(advisor.getEjbankCustomers()));
        } else if (ctm != null) {
            return Optional.of(List.of(em.find(EjbankCustomer.class, id)));
        } else {
            return Optional.empty();
        }
    }

    /**
     * Validates whether a user has access to a specific account.
     * If the user is an advisor, checks if the account belongs to one of their customers.
     * If the user is a customer, checks if the account belongs to them.
     *
     * @param accountId The identifier of the account to be validated.
     * @param userId The identifier of the user making the request.
     * @param user The user object making the request.
     *
     * @return An {@link Optional} containing an {@link ErrorIdentifier} if the user does not have access,
     *         or empty if the validation passes.
     */
    @Override
    public Optional<ErrorIdentifier> isInvalidUserAccount(long accountId, long userId, EjbankUser user) {
        var customers = getUserCustomers(user, userId).orElse(null);
        EjbankAccount account;
        EjbankCustomer customer;

        if (customers == null) {
            return Optional.of(ErrorIdentifier.USER_NOT_FOUND);
        }
        if (user instanceof EjbankAdvisor) {
            account = customers.stream()
                    .map(EjbankCustomer::getAccounts)
                    .flatMap(Collection::stream)
                    .filter(acc -> Objects.equals(acc.getId(), accountId))
                    .findFirst()
                    .orElse(null);
            if (account == null) {
                return Optional.of(ErrorIdentifier.ACCOUNT_NOT_ASSIGNED_TO_ADVISOR);
            }
        }
        customer = customers.get(0);
        account = customer.getAccounts().stream()
                .filter(acc -> acc.getId() == accountId)
                .findFirst()
                .orElse(null);
        if (account == null) {
            return Optional.of(ErrorIdentifier.ACCOUNT_NOT_ASSIGNED_TO_CUSTOMER);
        }
        return Optional.empty();
    }
}
