package com.ejbank.api;

import com.ejbank.model.UserResponse;
import com.ejbank.bean.UserBeanLocal;


import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class UserApi {

    @EJB
    private final UserBeanLocal userBeanLocal;

    @GET("/{user_id}")
    public UserResponse getPeople(@PathParam("user_id") Integer userId) {
        return userBeanLocal.getUser(userId);
    }
}
