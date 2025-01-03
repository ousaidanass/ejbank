package com.ejbank.dto.transaction;


import java.math.BigDecimal;

public class TransactionPreviewResponseDto {
    private Boolean result;
    private BigDecimal before;
    private BigDecimal after;
    private String message;

    private String error;

    public TransactionPreviewResponseDto(Boolean result, BigDecimal before, BigDecimal after, String message) {
        this.result = result;
        this.before = before;
        this.after = after;
        this.message = message;
    }

    public TransactionPreviewResponseDto(String error) {
        this.error = error;
    }

    public Boolean getResult() {
        return result;
    }

    public BigDecimal getBefore() {
        return before;
    }

    public BigDecimal getAfter() {
        return after;
    }

    public String getMessage() {
        return message;
    }

    public String getError() {
        return error;
    }
}
