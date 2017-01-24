package com.rk.capstone.model.services.user;

import com.rk.capstone.model.domain.User;

/**
 * Service Interface for User Domain Object
 */
public interface UserService {

    User saveUser(User user);

    User findByUserName(String userName);
}
