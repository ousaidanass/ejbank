package com.ejbank.api;

import com.ejbank.bean.AccountBeanLocal;
import com.ejbank.dto.account.AccountOverviewResponseDto;
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

    /**
     * Web service to retrieve the accounts of a specific user.
     *
     * @param userId The unique identifier of the user whose accounts need to be fetched.
     *
     * @return An {@link AccountsResponseDto} object containing a list of user accounts or an error message.
     *         If the user does not exist or a processing error occurs, the corresponding error message is returned.
     *
     * @throws TraitementException If a specific data processing error occurs.
     */
    @GET
    @Path("/{user_id}")
    public AccountsResponseDto<AccountResponseDto> getAccounts(@PathParam("user_id") Long userId) {
        try {
            return new AccountsResponseDto<>(accountBeanLocal.getAccounts(userId));
        } catch (TraitementException e) {
            return new AccountsResponseDto<>(ErrorMessages.getErrorMessage(e.getErrorIdentifier()));
        }
    }

    /**
     * Web service to retrieve all accounts associated with a user, including customer and account details.
     * This method supports both customers and advisors. For advisors, it retrieves accounts of their managed customers.
     * For customers, it retrieves their own accounts.
     *
     * @param userId The unique identifier of the user (customer or advisor).
     *
     * @return An {@link AccountsResponseDto} object containing a list of account details,
     *         or an error message if the user is not found or a processing failure occurs.
     *
     * @throws TraitementException If a specific processing error occurs, such as user not found.
     */
    @GET
    @Path("/all/{user_id}")
    public AccountsResponseDto<AccountOverviewResponseDto> getAllAccounts(@PathParam("user_id") Long userId) {
        try {
            return new AccountsResponseDto<>(accountBeanLocal.getAllAccounts(userId));
        } catch (TraitementException e) {
            return new AccountsResponseDto<>(ErrorMessages.getErrorMessage(e.getErrorIdentifier()));
        }
    }
}
