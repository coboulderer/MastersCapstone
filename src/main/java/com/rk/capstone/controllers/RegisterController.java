package com.rk.capstone.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rk.capstone.model.domain.User;
import com.rk.capstone.model.services.user.IUserService;

/**
 * REST Controller for /register endpoint
 */
@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public User registerNewUser(@RequestBody User user) {
        return userService.saveUser(user);
    }
}
