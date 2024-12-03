package com.ejbank.bean.impl;

import com.ejbank.bean.UserBeanLocal;
import com.ejbank.exception.TraitementException;
import com.ejbank.model.User;
import com.ejbank.dto.UserResponseDto;

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
        var user = em.find(User.class, id);
        if (user == null) {
            throw new TraitementException(1);
        }
        return new UserResponseDto(user.getFirstname(), user.getLastname());
    }
}
