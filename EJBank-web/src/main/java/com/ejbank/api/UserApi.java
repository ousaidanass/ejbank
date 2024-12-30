package com.ejbank.api;

import com.ejbank.dto.UserResponseDto;
import com.ejbank.bean.UserBeanLocal;
import com.ejbank.exception.ErrorMessages;
import com.ejbank.exception.TraitementException;


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
    private UserBeanLocal userBeanLocal;

    @GET
    @Path("/{user_id}")
    public UserResponseDto getPeople(@PathParam("user_id") Long userId) {
        UserResponseDto response = null;
        try {
            response = userBeanLocal.getUser(userId);
            return response;
        } catch (TraitementException e) {
            return new UserResponseDto(ErrorMessages.getErrorMessage(e.getErrorIdentifier()));
        }
    }
}
