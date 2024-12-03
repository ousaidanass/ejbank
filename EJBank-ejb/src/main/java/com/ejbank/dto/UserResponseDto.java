package com.ejbank.dto;

import java.util.Objects;


public class UserResponseDto {

    private String firstname;

    private String lastname;

    private String error;

    public UserResponseDto(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public UserResponseDto(String error) {
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
