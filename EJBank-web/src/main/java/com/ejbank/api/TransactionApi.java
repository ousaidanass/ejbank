package com.ejbank.api;

import com.ejbank.bean.TransactionBean;
import com.ejbank.dto.transaction.TransactionsDto;
import com.ejbank.exception.ErrorMessages;
import com.ejbank.exception.TraitementException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/transaction")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TransactionApi {
    @EJB
    private TransactionBean transactionBean;

    @GET
    @Path("/list/{account_id}/{offset}/{user_id}")
    public TransactionsDto userTransactions(@PathParam("account_id") Integer accountId, @PathParam("offset") Integer offset, @PathParam("user_id") Integer userId) {
        try {
            return transactionBean.getTransactionList(userId, accountId, offset);
        } catch (TraitementException e) {
            return new TransactionsDto(ErrorMessages.getErrorMessage(e.getErrorIdentifier()));
        }
    }
}
