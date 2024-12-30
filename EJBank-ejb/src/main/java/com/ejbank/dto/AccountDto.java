package com.ejbank.dto;


import java.math.BigDecimal;

public class AccountDto implements AccountDispatchDto {

    private Long id;
    private String user;
    private String type;
    private BigDecimal amount;
    private Integer validation;

    public AccountDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getValidation() {
        return validation;
    }

    public void setValidation(Integer validation) {
        this.validation = validation;
    }

    @Override
    public String toString() {
        return "AccountsAttachedResponseDto{" +
                "id=" + id +
                ", user='" + user + '\'' +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                ", validation=" + validation +
                '}';
    }
}
