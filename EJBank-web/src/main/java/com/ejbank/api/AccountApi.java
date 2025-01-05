package com.ejbank.api;

import com.ejbank.bean.AccountBeanLocal;
import com.ejbank.dto.account.AccountAdvisorResponseDto;
import com.ejbank.dto.account.AccountResponseDto;
import com.ejbank.dto.account.AccountsAttachedResponseDto;
import com.ejbank.dto.account.AccountsResponseDto;
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
        try {
            System.err.println("UserId AccountsAttached: " + userId);
            var accounts = accountBeanLocal.getAccountsAttached(userId);
            System.err.println("AccountsAttached: " + accounts);
            return accounts;
        } catch (TraitementException e) {
            return new AccountsAttachedResponseDto(ErrorMessages.getErrorMessage(e.getErrorIdentifier()));
        }
    }

    @GET
    @Path("/{user_id}")
    public AccountsResponseDto<AccountResponseDto> getAccounts(@PathParam("user_id") Long userId) {
        try {
            return new AccountsResponseDto<>(accountBeanLocal.getAccounts(userId));
        } catch (TraitementException e) {
            return new AccountsResponseDto<>(ErrorMessages.getErrorMessage(e.getErrorIdentifier()));
        }
    }

    @GET
    @Path("/all/{user_id}")
    public AccountsResponseDto<AccountAdvisorResponseDto> getAllAccounts(@PathParam("user_id") Long userId) {
        try {
            return new AccountsResponseDto<>(accountBeanLocal.getAllAccounts(userId));
        } catch (TraitementException e) {
            return new AccountsResponseDto<>(ErrorMessages.getErrorMessage(e.getErrorIdentifier()));
        }
    }
}
