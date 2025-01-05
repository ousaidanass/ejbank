package com.ejbank.dto.transaction;

public class TransactionValidationRequestDto {

    private Long transaction;
    private Boolean approve;
    private Long author;

    public TransactionValidationRequestDto() {
    }

    public TransactionValidationRequestDto(Long transaction, Boolean approve, Long author) {
        this.transaction = transaction;
        this.approve = approve;
        this.author = author;
    }

    public Long getTransaction() {
        return transaction;
    }

    public void setTransaction(Long transaction) {
        this.transaction = transaction;
    }

    public Boolean getApprove() {
        return approve;
    }

    public void setApprove(Boolean approve) {
        this.approve = approve;
    }

    public Long getAuthor() {
        return author;
    }

    public void setAuthor(Long author) {
        this.author = author;
    }
}
