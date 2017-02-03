package com.rk.capstone.controllers.login;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.rk.capstone.model.domain.User;
import com.rk.capstone.model.services.auth.AuthService;
import com.rk.capstone.model.services.user.UserService;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Class Provides Unit Testing for LoginController
 */
@RunWith(SpringRunner.class)
@WebMvcTest(LoginController.class)
public class LoginControllerTest {

    @MockBean
    private UserService userService;
    @MockBean
    private AuthService authService;

    @Autowired
    private MockMvc mockMvc;

    private String jsonCreds;
    private String userName;
    private String password;
    private String badRequestResponseBody;
    private String unauthorizedResponseBody;
    private User testUser;

    @Before
    public void setUp() {
        userName = "testuser";
        password = "abc123";
        badRequestResponseBody = "Both a username and password must be provided";
        unauthorizedResponseBody = "Bad username & password combination, try again";
        testUser = new User("test", "user", "user@test.com", userName, password, null);
    }

    @Test
    public void testLoginUserValidCredentials() throws Exception {
        jsonCreds = "{\"username\":\"" + userName + "\", \"password\":\"" + password + "\"}";
        given(this.userService.getUserByUserName(testUser.getUserName())).willReturn(testUser);
        given(this.authService.getAuthToken(testUser.getUserName())).willReturn("ValidAuthToken");

        MvcResult result = this.mockMvc.perform(post("/api/login/user").content(jsonCreds).
                contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isCreated()).
                andDo(print()).
                andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Assert.assertTrue(responseBody.length() > 0);
    }

    @Test
    public void testLoginUserBadPassword() throws Exception {
        jsonCreds = "{\"username\":\"" + userName + "\", \"password\":\"badpassword\"}";
        given(this.userService.getUserByUserName(testUser.getUserName())).willReturn(testUser);

        MvcResult result = this.mockMvc.perform(post("/api/login/user").content(jsonCreds).
                contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isUnauthorized()).
                andDo(print()).
                andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Assert.assertTrue(responseBody.equals(unauthorizedResponseBody));
    }

    @Test
    public void testLoginUserEmptyUserName() throws Exception {
        jsonCreds = "{\"username\":\"\", \"password\":\"" + password + "\"}";
        MvcResult result = this.mockMvc.perform(post("/api/login/user").content(jsonCreds).
                contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isBadRequest()).
                andDo(print()).
                andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Assert.assertTrue(responseBody.equals(badRequestResponseBody));
    }


    @Test
    public void testLoginUserEmptyPassword() throws Exception {
        jsonCreds = "{\"username\":\"" + userName + "\", \"password\":\"\"}";
        MvcResult result = this.mockMvc.perform(post("/api/login/user").content(jsonCreds).
                contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isBadRequest()).
                andDo(print()).
                andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Assert.assertTrue(responseBody.equals(badRequestResponseBody));
    }
}
