package com.rk.capstone.controllers.login;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rk.capstone.model.domain.User;
import com.rk.capstone.model.services.auth.IAuthService;
import com.rk.capstone.model.services.user.IUserService;

/**
 * REST Controller for /login endpoint
 */
@RestController
@RequestMapping(value = "/api/login")
public class LoginController {

    private IUserService userService;
    private IAuthService authService;

    public LoginController(IUserService userService, IAuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseEntity<String> loginUser(@RequestBody Map<String, String> credentials) {

        ResponseEntity<String> response;
        String userName = credentials.get("username");
        String password = credentials.get("password");

        if (userName == null || password == null || userName.isEmpty() || password.isEmpty()) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).
                    body("Both a username and password must be provided");
        } else {
            User user = userService.findByUserName(userName);
            if (user == null) {
                response = ResponseEntity.status(HttpStatus.NOT_FOUND).
                        body("The provided username could not be found");
            } else if (!user.getPassword().equals(password)) {
                response = ResponseEntity.status(HttpStatus.UNAUTHORIZED).
                        body("Incorrect password, try again");
            } else {
                String authToken = authService.getAuthToken(userName);
                response = ResponseEntity.status(HttpStatus.CREATED).body(authToken);
            }
        }
        return response;
    }

}
