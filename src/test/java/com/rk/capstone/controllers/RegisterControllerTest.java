package com.rk.capstone.controllers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rk.capstone.model.domain.User;
import com.rk.capstone.model.services.user.IUserService;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Class Provides Unit Testing for RegisterController
 */
@RunWith(SpringRunner.class)
@WebMvcTest(RegisterController.class)
public class RegisterControllerTest {

    @MockBean
    private IUserService userService;

    @Autowired
    private MockMvc mockMvc;

    private User user;
    private String userJson;
    private ObjectMapper objectMapper;

    @Before
    public void setup() {
        user = new User("rick", "k", "rick@email.com", "rkow", "abc123");
        objectMapper = new ObjectMapper();
        try {
            userJson = objectMapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
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

    @Test
    public void testRegisterNewUserPostResponse() throws Exception {
        given(this.userService.findByUserName(user.getUserName())).willReturn(null);
        given(this.userService.saveUser(any(User.class))).willReturn(user);
        Assert.assertNotNull("Mocked UserService is Null", this.userService);

        MvcResult result = this.mockMvc.perform(post("/register/user").content(userJson).
                contentType(MediaType.APPLICATION_JSON).
                accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isCreated()).
                andDo(print()).
                andReturn();

        String responseBody = result.getResponse().getContentAsString();
        User responseUser = objectMapper.readValue(responseBody, User.class);

        Assert.assertEquals("Unexpected First Name", user.getFirstName(),
                responseUser.getFirstName());
        Assert.assertEquals("Unexpected Last Name", user.getLastName(), responseUser.getLastName());
        Assert.assertEquals("Unexpected Email Address", user.getEmailAddress(),
                responseUser.getEmailAddress());
        Assert.assertEquals("Unexpected Username", user.getUserName(), responseUser.getUserName());
        Assert.assertEquals("Unexpected Password", user.getPassword(), responseUser.getPassword());

    }

    @Test
    public void testRegisterNewUserDuplicateUserNameResponse() throws Exception {
        given(this.userService.findByUserName(user.getUserName())).willReturn(user);
        this.mockMvc.perform(post("/register/user").content(userJson).
                contentType(MediaType.APPLICATION_JSON).
                accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isConflict()).
                andDo(print());
    }
}