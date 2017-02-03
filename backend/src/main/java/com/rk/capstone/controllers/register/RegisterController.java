package com.rk.capstone.controllers.register;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rk.capstone.model.domain.User;
import com.rk.capstone.model.services.user.UserService;

/**
 * REST Controller for /register endpoint
 */
@RestController
@RequestMapping("/api/register")
public class RegisterController {

    private User user;

    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseEntity<User> registerNewUser(@RequestBody User user) {
        this.user = user;
        if (isNewUser()) {
            createNewUser();
            clearUserPassword();
            return getUserCreatedResponse();
        } else {
            return getUserConflictResponse();
        }
    }

    private boolean isNewUser() {
        return userService.getUserByUserName(user.getUserName()) == null;
    }

    private void createNewUser() {
        logger.info("Creating new user: " + user.getUserName());
        user = userService.saveUser(user);
    }

    private void clearUserPassword() {
        user.setPassword("");
    }

    private ResponseEntity<User> getUserConflictResponse() {
        logger.error("The desired userName: " + user.getUserName() + " already exists!");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
    }

    private ResponseEntity<User> getUserCreatedResponse() {
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
