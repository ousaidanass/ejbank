package com.ejbank.dto.account;


import com.ejbank.dto.AccountDispatchDto;

import java.math.BigDecimal;
import java.util.List;

public class AccountDetailResponseDto {
    private String owner;

    private String advisor;

    private BigDecimal rate;

    private BigDecimal interest;

    private BigDecimal amount;

    private String error;

    public AccountDetailResponseDto(String owner, String advisor, BigDecimal rate, BigDecimal interest, BigDecimal amount) {
        this.owner = owner;
        this.advisor = advisor;
        this.rate = rate;
        this.interest = interest;
        this.amount = amount;
    }

    public AccountDetailResponseDto(String error) {
        this.error = error;
    }

    public String getOwner() {
        return owner;
    }

    public String getAdvisor() {
        return advisor;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public BigDecimal getInterest() {
        return interest;
    }


    public BigDecimal getAmount() {
        return amount;
    }

    public String getError() {
        return error;
    }

    @Override
    public String toString() {
        return "AccountDetailResponseDto{" +
                "owner='" + owner + '\'' +
                ", advisor='" + advisor + '\'' +
                ", rate=" + rate +
                ", interest=" + interest +
                ", amount=" + amount +
                ", error='" + error + '\'' +
                '}';
    }
}
