package com.ejbank.api;

import com.ejbank.bean.AccountBeanLocal;
import com.ejbank.dto.account.AccountDetailResponseDto;
import com.ejbank.dto.account.AccountsAttachedResponseDto;
import com.ejbank.exception.ErrorMessages;
import com.ejbank.exception.TraitementException;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/account")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class AccountDetailApi {

    @EJB
    private AccountBeanLocal accountBeanLocal;

    @GET
    @Path("/{account_id}/{user_id}")
    public AccountDetailResponseDto getAccountDetail(@PathParam("account_id") Long accountId, @PathParam("user_id") Long userId) {

        try {
            System.err.println("Api getAccountDetail called with account_id='" + accountId + "' and user_id='" + userId + "'");
            var accountDetail = accountBeanLocal.getAccountDetail(accountId, userId);
            System.err.println("Account detail: " + accountDetail);
            return accountDetail;
        } catch (TraitementException e) {
            return new AccountDetailResponseDto(ErrorMessages.getErrorMessage(e.getErrorIdentifier()));
        }
    }

}
