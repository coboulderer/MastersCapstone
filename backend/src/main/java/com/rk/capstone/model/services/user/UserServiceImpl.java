package com.rk.capstone.model.services.user;

import org.springframework.stereotype.Service;

import com.rk.capstone.model.dao.UserDao;
import com.rk.capstone.model.domain.User;

/**
 * Implementation of User Service
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User saveUser(User user) {
        return userDao.save(user);
    }

    @Override
    public User getUserByUserName(String userName) {
        return userDao.findByUserName(userName);
    }
}
