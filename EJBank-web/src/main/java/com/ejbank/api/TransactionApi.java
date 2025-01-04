package com.ejbank.api;

import com.ejbank.bean.TransactionBean;
import com.ejbank.dto.account.AccountDetailResponseDto;
import com.ejbank.dto.transaction.*;
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

    @POST
    @Path("/preview")
    @Consumes(MediaType.APPLICATION_JSON)
    public TransactionPreviewResponseDto previewTransaction(TransactionPreviewRequestDto requestDto) {
        try {
            System.err.println("Api previewTransaction called with transactionRequest='" + requestDto + "'");
            var transactionPreview = transactionBean.previewTransaction(requestDto);
            System.err.println("Transaction preview: " + transactionPreview);
            return transactionPreview;
        } catch (TraitementException e) {
            return new TransactionPreviewResponseDto(ErrorMessages.getErrorMessage(e.getErrorIdentifier()));
        }
    }

    @POST
    @Path("/validation")
    @Consumes(MediaType.APPLICATION_JSON)
    public TransactionValidationResponseDto validateTransaction(TransactionValidationRequestDto requestDto) {
        try {
            System.err.println("Api validateTransaction called with transactionRequest='" + requestDto + "'");
            var transactionValidation = transactionBean.validateTransaction(requestDto);
            System.err.println("Transaction validation: " + transactionValidation);
            return transactionValidation;
        } catch (TraitementException e) {
            return new TransactionValidationResponseDto(ErrorMessages.getErrorMessage(e.getErrorIdentifier()));
        }
    }
}
