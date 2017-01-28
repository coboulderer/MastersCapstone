package com.rk.capstone.controllers.login;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rk.capstone.model.domain.User;
import com.rk.capstone.model.services.auth.AuthService;
import com.rk.capstone.model.services.user.UserService;

/**
 * REST Controller for /login endpoint
 */
@RestController
@RequestMapping(value = "/api/login")
public class LoginController {

    private String userName;
    private String password;
    private String authToken;

    private final UserService userService;
    private final AuthService authService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public LoginController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseEntity<String> loginUser(@RequestBody Map<String, String> credentials) {
        logger.info("Processing User Login Request");
        setUsernameAndPassword(credentials);
        ResponseEntity<String> response;
        if (areCredentialsProvided()) {
            createUserAuthToken();
            response = getLoginAttemptResponse();
        } else {
            response = getBadRequestResponse();
        }
        return response;
    }

    private void setUsernameAndPassword(Map<String, String> credentials) {
        this.userName = credentials.get("username");
        this.password = credentials.get("password");
    }

    private boolean areCredentialsProvided() {
        return userName != null && password != null && !userName.isEmpty() && !password.isEmpty();
    }

    private void createUserAuthToken() {
        User user = userService.getUserByUserName(userName);
        if (user != null && isUserPasswordValid(user.getPassword())) {
            logger.info("User found & Valid login credentials provided, generating Auth Token");
            authToken = authService.getAuthToken(userName);
        } else {
            logger.error("The provided Username & Password combination could not be found");
            authToken = null;
        }
    }

    private boolean isUserPasswordValid(String password) {
        return password.equals(this.password);
    }

    private ResponseEntity<String> getLoginAttemptResponse() {
        ResponseEntity<String> response;
        if (authToken != null) {
            response = ResponseEntity.status(HttpStatus.CREATED).body(authToken);
        } else {
            response = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Bad username & password" +
                    " combination, try again");
        }
        return response;
    }

    private ResponseEntity<String> getBadRequestResponse() {
        logger.error("The provided userName and password are either null or empty");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Both a username and password must be provided");
    }
}
