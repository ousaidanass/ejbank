package com.ejbank.dto.transaction;


import java.math.BigDecimal;

public class TransactionValidationResponseDto {
    private Boolean result;
    private String message;

    private String error;

    public TransactionValidationResponseDto(Boolean result, String message) {
        this.result = result;
        this.message = message;
    }

    public TransactionValidationResponseDto(String error) {
        this.error = error;
    }

    public Boolean getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public String getError() {
        return error;
    }
}
