package com.ejbank.bean.impl;

import com.ejbank.bean.UserBeanLocal;
import com.ejbank.exception.ErrorIdentifier;
import com.ejbank.exception.TraitementException;
import com.ejbank.dto.UserResponseDto;
import com.ejbank.model.EjbankUser;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class UserBeanImpl implements UserBeanLocal {
    @PersistenceContext(name = "EJBankDS")
    private EntityManager em;

    @Override
    public UserResponseDto getUser(long id) throws TraitementException {
        var user = em.find(EjbankUser.class, id);
        if (user == null) {
            throw new TraitementException(ErrorIdentifier.USER_NOT_FOUND);
        }
        return new UserResponseDto(user.getFirstname(), user.getLastname());
    }
}
