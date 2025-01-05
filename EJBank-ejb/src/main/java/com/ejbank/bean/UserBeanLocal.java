package com.ejbank.bean;

import com.ejbank.dto.UserResponseDto;
import com.ejbank.exception.TraitementException;

import javax.ejb.Local;

@Local
public interface UserBeanLocal {
    UserResponseDto getUser(long id) throws TraitementException;
}
