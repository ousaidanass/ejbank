package com.ejbank.bean;

import com.ejbank.dto.transaction.*;
import com.ejbank.exception.TraitementException;

import javax.ejb.Local;

@Local
public interface TransactionBean {
    int PAGINATION = 5;
    TransactionsResponseDto<TransactionResponseDto> getTransactionList(long userId, long accountId, int offset) throws TraitementException;
    TransactionPreviewResponseDto previewTransaction(TransactionPreviewRequestDto requestDto) throws TraitementException;
    TransactionValidationResponseDto validateTransaction(TransactionValidationRequestDto requestDto) throws TraitementException;
    String getPendingTransactionCount(long userId) throws TraitementException;
    TransactionValidationResponseDto applyTransaction(TransactionApplyDto request) throws TraitementException;
}
