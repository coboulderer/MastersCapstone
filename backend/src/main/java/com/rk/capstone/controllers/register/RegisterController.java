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
import com.rk.capstone.model.services.user.IUserService;

/**
 * REST Controller for /register endpoint
 */
@RestController
@RequestMapping("/api/register")
public class RegisterController {

    private final IUserService userService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public RegisterController(IUserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseEntity<User> registerNewUser(@RequestBody User user) {
        ResponseEntity<User> response;
        logger.info("Attempting to register new user");
        if (userService.findByUserName(user.getUserName()) == null) {
            logger.info("Creating new user: " + user.getUserName());
            user = userService.saveUser(user);
            user.setPassword("");
            response = ResponseEntity.status(HttpStatus.CREATED).body(user);
        } else {
            logger.error("The desired userName: " + user.getUserName() + " already exists!");
            response = ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        return response;
    }
}
