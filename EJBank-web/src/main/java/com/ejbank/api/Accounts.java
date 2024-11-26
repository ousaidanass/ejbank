package com.ejbank.api;

import com.ejbank.model.UserResponse;
import com.ejbank.test.UserBeanLocal;


import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class Accounts {

    @EJB
    private final UserBeanLocal userBeanLocal;

    @GET
    public UserResponse getPeople() {
        return userBeanLocal.get
    }
}
