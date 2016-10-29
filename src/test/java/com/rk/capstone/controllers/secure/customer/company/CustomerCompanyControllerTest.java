package com.rk.capstone.controllers.secure.customer.company;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.rk.capstone.model.domain.CustomerCompany;
import com.rk.capstone.model.services.customer.company.ICustomerCompanyService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test Class for CustomerCompanyController
 */
@RunWith(SpringRunner.class)
@WebMvcTest(CustomerCompanyController.class)
public class CustomerCompanyControllerTest {

    @MockBean
    private ICustomerCompanyService customerCompanyService;

    @Autowired
    private MockMvc mockMvc;

    private CustomerCompany customerCompanyOne;
    private CustomerCompany customerCompanyTwo;
    private List<CustomerCompany> customerCompanies;

    private String authToken;
    private ObjectMapper objectMapper;

    @Before
    public void setup() {
        customerCompanyOne = new CustomerCompany("Company One", "We make widgets", "Dominate the " +
                "widget market");
        customerCompanyTwo = new CustomerCompany("Company Two", "We make gadgets", "Number one " +
                "gadget maker in the world");
        customerCompanies = new ArrayList<>();
        customerCompanies.add(customerCompanyOne);
        customerCompanies.add(customerCompanyTwo);

        authToken = Jwts.builder().setSubject("username").claim("roles", "user").
                setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256, "secretkey").
                compact();

        objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetAllCustomerCompaniesNoContent() throws Exception {
        given(this.customerCompanyService.getAllCustomerCompanies()).willReturn(null);

        MvcResult result = this.mockMvc.perform(get("/api/secure/customer/company").
                header("auth-token", authToken).
                contentType(MediaType.APPLICATION_JSON).
                accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isNoContent()).
                andDo(print()).
                andReturn();
    }

    @Test
    public void testGetAllCustomerCompaniesHappyPath() throws Exception {
        given(this.customerCompanyService.getAllCustomerCompanies()).willReturn(customerCompanies);

        MvcResult result = this.mockMvc.perform(get("/api/secure/customer/company").
                header("auth-token", authToken).
                contentType(MediaType.APPLICATION_JSON).
                accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).
                andDo(print()).
                andReturn();
    }

    @Test
    public void testCreateNewCustomerCompanyNullResponse() throws Exception {
        given(this.customerCompanyService.saveCustomerCompany(any(CustomerCompany.class))).
                willReturn(null);
        String customerCompanyJson = objectMapper.writeValueAsString(customerCompanyOne);

        MvcResult result = this.mockMvc.perform(post("/api/secure/customer/company").
                header("auth-token", authToken).
                contentType(MediaType.APPLICATION_JSON).
                content(customerCompanyJson).
                accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isInternalServerError()).
                andDo(print()).
                andReturn();
    }

    @Test
    public void testCreateNewCustomerCompanyHappyPath() throws Exception {
        given(this.customerCompanyService.saveCustomerCompany(any(CustomerCompany.class))).
                willReturn(customerCompanyOne);
        customerCompanyOne.setId(1L);
        String customerCompanyJson = objectMapper.writeValueAsString(customerCompanyOne);

        MvcResult result = this.mockMvc.perform(post("/api/secure/customer/company").
                header("auth-token", authToken).
                contentType(MediaType.APPLICATION_JSON).
                content(customerCompanyJson).
                accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isCreated()).
                andDo(print()).
                andReturn();
    }

    @Test
    public void testDeleteCustomerCompanyNotFound() throws Exception {
        given(this.customerCompanyService.getCustomerCompanyById(any(Long.class))).
                willReturn(null);

        MvcResult result = this.mockMvc.perform(delete("/api/secure/customer/company/1").
                header("auth-token", authToken).
                contentType(MediaType.APPLICATION_JSON).
                accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isNotFound()).
                andDo(print()).
                andReturn();
    }

    @Test
    public void testDeleteCustomerCompanyHappyPath() throws Exception {
        given(this.customerCompanyService.getCustomerCompanyById(any(Long.class))).
                willReturn(customerCompanyOne);

        MvcResult result = this.mockMvc.perform(delete("/api/secure/customer/company/1").
                header("auth-token", authToken).
                contentType(MediaType.APPLICATION_JSON).
                accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).
                andDo(print()).
                andReturn();
    }
}
