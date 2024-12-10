package com.ejbank.dto;


public class AccountsAttachedResponseDto {

    private String firstname;

    private String lastname;

    private String error;

    public AccountsAttachedResponseDto(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public AccountsAttachedResponseDto(String error) {
        this.error = error;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getError() {return error;}
}
