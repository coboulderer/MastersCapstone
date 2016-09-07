package com.rk.capstone.controllers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rk.capstone.model.domain.User;
import com.rk.capstone.model.services.user.IUserService;

import static org.mockito.BDDMockito.given;

/**
 * Class Provides Unit Testing for RegisterController
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class RegisterControllerTest {

    @MockBean
    private IUserService userService;

    private User user;

    @Before
    public void setup() {
        user = new User("rick", "k", "rick@email.com", "rkow", "abc123");
    }

    @Test
    public void testRegisterNewUserOkResponse() {
        given(this.userService.findByUserName(user.getUserName())).willReturn(null);
        given(this.userService.saveUser(user)).willReturn(user);
        Assert.assertNotNull("Mocked UserService is Null", userService);

        RegisterController registerController = new RegisterController(userService);
        ResponseEntity<User> response = registerController.registerNewUser(user);

        Assert.assertEquals("Unexpected HTTP Status Code Received", HttpStatus.CREATED,
                response.getStatusCode());

        User returnedUser = response.getBody();
        Assert.assertEquals("Unexpected First Name", user.getFirstName(),
                returnedUser.getFirstName());
        Assert.assertEquals("Unexpected Last Name", user.getLastName(), returnedUser.getLastName());
        Assert.assertEquals("Unexpected Email Address", user.getEmailAddress(),
                returnedUser.getEmailAddress());
        Assert.assertEquals("Unexpected Username", user.getUserName(), returnedUser.getUserName());
        Assert.assertEquals("Unexpected Password", user.getPassword(), returnedUser.getPassword());
    }

    @Test
    public void testRegisterNewUserConflictResponse() {
        given(this.userService.findByUserName(user.getUserName())).willReturn(user);
        Assert.assertNotNull("Mocked UserService is Null", userService);

        RegisterController registerController = new RegisterController(userService);
        ResponseEntity<User> response = registerController.registerNewUser(user);

        Assert.assertEquals("Unexpected HTTP Status Code Received", HttpStatus.CONFLICT, response
                .getStatusCode());
        User returnedUser = response.getBody();
        Assert.assertNull(returnedUser);
    }

}
