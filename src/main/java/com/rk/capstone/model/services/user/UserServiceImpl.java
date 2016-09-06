package com.rk.capstone.model.services.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rk.capstone.model.dao.UserDao;
import com.rk.capstone.model.domain.User;

/**
 * Implementation of User Service
 */
@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    private UserDao userDao;

    @Override
    public User saveUser(User user) {
        return userDao.save(user);
    }
}
