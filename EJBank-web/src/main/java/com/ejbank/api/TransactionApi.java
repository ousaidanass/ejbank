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
public class TransactionApi {
    @EJB
    private TransactionBean transactionBean;

    /**
     * Web service to retrieve a paginated list of transactions for a specific account and user.
     *
     * @param accountId The unique identifier of the account for which transactions are requested.
     * @param offset The starting point for pagination of the transaction list.
     * @param userId The unique identifier of the user requesting the transaction list.
     *
     * @return A JSON string representing a {@link TransactionsResponseDto} object containing transaction details,
     *         or an error message if the user is unauthorized or another processing error occurs.
     *
     * @throws TraitementException If the user is not authorized to access the specified account, or any other validation fails.
     */
    @GET
    @Path("/list/{account_id}/{offset}/{user_id}")
    public String userTransactions(@PathParam("account_id") Integer accountId, @PathParam("offset") Integer offset, @PathParam("user_id") Integer userId) {
        try {
            var response = transactionBean.getTransactionList(userId, accountId, offset);
            return response.toString();
        } catch (TraitementException e) {
            return new TransactionsResponseDto<>(ErrorMessages.getErrorMessage(e.getErrorIdentifier())).toString();
        }
    }

    /**
     * Web service to retrieve the count of pending transactions requiring validation for a specific user.
     *
     * @param id The unique identifier of the user for whom the count of pending transactions is requested.
     *
     * @return A string representing the number of pending transactions requiring approval,
     *         or an error message if a processing failure occurs.
     *
     * @throws TraitementException If the user cannot be found or any validation error occurs.
     */
    @GET
    @Path("/validation/notification/{user_id}")
    public String getPendingTransactions(@PathParam("user_id") Integer id){
        try {
            return transactionBean.getPendingTransactionCount(id);
        } catch (TraitementException e) {
            return ErrorMessages.getErrorMessage(e.getErrorIdentifier());
        }
    }

    /**
     * Web service to apply a transaction request between accounts.
     *
     * @param requestDto A {@link TransactionApplyDto} object containing the transaction details, including source and destination accounts, amount, and author.
     *
     * @return A {@link TransactionValidationResponseDto} object indicating whether the transaction was successfully validated,
     *         or an error message if validation or processing fails.
     *
     * @throws TraitementException If the user is unauthorized to apply the transaction or the transaction is deemed invalid.
     */
    @POST
    @Path("/apply")
    @Consumes(MediaType.APPLICATION_JSON)
    public TransactionValidationResponseDto previewTransaction(TransactionApplyDto requestDto) {
        try {
            return transactionBean.applyTransaction(requestDto);
        } catch (TraitementException e) {
            return new TransactionValidationResponseDto(ErrorMessages.getErrorMessage(e.getErrorIdentifier()));
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
