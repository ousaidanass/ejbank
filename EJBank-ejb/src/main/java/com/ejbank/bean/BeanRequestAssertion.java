package com.ejbank.bean;

import com.ejbank.exception.ErrorIdentifier;
import com.ejbank.model.EjbankCustomer;
import com.ejbank.model.EjbankUser;

import javax.ejb.Local;
import java.util.List;
import java.util.Optional;

@Local
public interface BeanRequestAssertion {
    boolean isAdvisor(EjbankUser user);
    boolean isCustomer(EjbankUser user);
    Optional<List<EjbankCustomer>> getUserCustomers(EjbankUser user, long id);

    Optional<ErrorIdentifier> isInvalidUserAccount(long accountId, long userId, EjbankUser user);
}
