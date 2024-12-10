package com.ejbank.api;

import com.ejbank.bean.AccountBeanLocal;
import com.ejbank.bean.UserBeanLocal;
import com.ejbank.dto.AccountsAttachedResponseDto;
import com.ejbank.dto.UserResponseDto;
import com.ejbank.exception.ErrorMessages;
import com.ejbank.exception.TraitementException;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class AccountApi {

    @EJB
    private AccountBeanLocal accountBeanLocal;

    @GET
    @Path("/attached/{user_id}")
    public AccountsAttachedResponseDto getAccountsAttached(@PathParam("user_id") Long userId) {

        AccountsAttachedResponseDto accounts = null;

        try {
            System.err.println("UserId AccountsAttached: " + userId);
            accounts = accountBeanLocal.getAccountsAttached(userId);
            System.err.println("AccountsAttached: " + accounts);
            return accounts;
        } catch (TraitementException e) {
            return new AccountsAttachedResponseDto(ErrorMessages.getErrorMessage(e.getCode()));
        }
    }
}
