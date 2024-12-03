package com.ejbank.model;

import java.util.Objects;

public class UserResponse {

    private final String firstName;

    private final String lastName;
    public UserResponse(String firstName, String lastName) {
        this.firstName = Objects.requireNonNull(firstName);
        this.lastName = Objects.requireNonNull(lastName);
    }
}
