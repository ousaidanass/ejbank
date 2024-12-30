package com.ejbank.bean.impl;

import com.ejbank.model.EjbankAdvisor;
import com.ejbank.model.EjbankCustomer;
import com.ejbank.model.EjbankUser;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
public class BeanRequestAssertionImpl {
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
}
