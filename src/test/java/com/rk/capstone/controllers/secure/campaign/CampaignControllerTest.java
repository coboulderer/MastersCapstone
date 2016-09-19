package com.rk.capstone.controllers.secure.campaign;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rk.capstone.model.domain.Campaign;
import com.rk.capstone.model.domain.User;
import com.rk.capstone.model.services.campaign.ICampaignService;
import com.rk.capstone.model.services.user.IUserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Class Provides Unit Testing for CampaignController
 */
@RunWith(SpringRunner.class)
@WebMvcTest(CampaignController.class)
public class CampaignControllerTest {

    @MockBean
    private IUserService userService;
    @MockBean
    private ICampaignService campaignService;

    @Autowired
    private MockMvc mockMvc;

    private String userName;
    private User user;
    private Campaign campaignOne;
    private Campaign campaignTwo;
    private List<Campaign> campaigns;
    private String authToken;

    private ObjectMapper objectMapper;

    @Before
    public void setup() {
        userName = "JohnDoe";
        user = new User("John", "Doe", "johndoe@email.com", userName, "abc123", null);
        campaignOne = new Campaign("campaignOne", "commit", "summary here", "pending", new Date
                (), new Date(), 100000, user);
        campaignTwo = new Campaign("campaignTwo", "upside", "summary here", "pending", new Date
                (), new Date(), 200000, user);
        campaigns = new ArrayList<>();
        campaigns.add(campaignOne);
        campaigns.add(campaignTwo);
        user.setCampaigns(campaigns);

        authToken = Jwts.builder().setSubject(userName).claim("roles", "user").
                setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256, "secretkey").
                compact();

        objectMapper = new ObjectMapper();
    }

    @Test
    public void testUserNameDoesNotExistResponse() throws Exception {
        given(this.userService.findByUserName(userName)).willReturn(null);
        MvcResult result = this.mockMvc.perform(get("/api/secure/campaign/" + userName).
                header("auth-token", authToken).
                accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isNotFound()).
                andDo(print()).
                andReturn();
    }

    @Test
    public void testGetAllUserCampaigns() throws Exception {
        given(this.userService.findByUserName(userName)).willReturn(user);
        given(this.campaignService.getOwnedCampaigns(any(User.class))).willReturn(campaigns);

        MvcResult result = this.mockMvc.perform(get("/api/secure/campaign/" + userName).
                header("auth-token", authToken).
                accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).
                andDo(print()).
                andReturn();

        String responseBody = result.getResponse().getContentAsString();

        Campaign[] campaigns = objectMapper.readValue(responseBody, Campaign[].class);
        Assert.assertTrue(campaigns.length == 2);
    }
}
