package com.ejbank.dto.account;


import java.math.BigDecimal;

public class AccountDto {

    private Long id;
    private String user;
    private String type;
    private BigDecimal amount;
    private Long validation;

    public AccountDto() {
    }

    public AccountDto(Long id, String user, String type, BigDecimal amount, Long validation) {
        this.id = id;
        this.user = user;
        this.type = type;
        this.amount = amount;
        this.validation = validation;
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

    public Long getValidation() {
        return validation;
    }

    public void setValidation(Long validation) {
        this.validation = validation;
    }

    @Override
    public String toString() {
        return "AccountDto{" +
                "id=" + id +
                ", user='" + user + '\'' +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                ", validation=" + validation +
                '}';
    }
}
