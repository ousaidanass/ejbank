package com.ejbank.test;

import com.ejbank.model.UserResponse;

import javax.ejb.Local;

@Local
public interface UserBeanLocal {
    UserResponse getUser(int id);
}
