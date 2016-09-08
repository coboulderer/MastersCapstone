package com.rk.capstone.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/register")
public class RegisterController {

    private final IUserService userService;

    public RegisterController(IUserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseEntity<User> registerNewUser(@RequestBody User user) {
        if (userService.findByUserName(user.getUserName()) == null) {
            user = userService.saveUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }
}
