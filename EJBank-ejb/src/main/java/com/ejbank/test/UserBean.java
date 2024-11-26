package com.ejbank.test;

import com.ejbank.model.UserResponse;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.registry.infomodel.User;

@Stateless
@LocalBean
public class UserBean implements UserBeanLocal {
    @PersistenceContext(name = "EJBankDS")
    private EntityManager em;

    @Override
    public UserResponse getUser(int id) {
        var user = em.find(User.class, id);
        return new UserResponse();
    }
}
