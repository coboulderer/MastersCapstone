package com.rk.capstone.model.services.customer.company;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rk.capstone.model.dao.CustomerCompanyDao;
import com.rk.capstone.model.domain.CustomerCompany;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

/**
 * Test Class for CustomerCompany Service
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class CustomerCompanyServiceTest {

    private CustomerCompanyService customerCompanyService;
    private CustomerCompanyDao customerCompanyDao = mock(CustomerCompanyDao.class);
    private CustomerCompany customerCompanyOne;
    private CustomerCompany customerCompanyTwo;
    private List<CustomerCompany> customerCompanies;

    @Before
    public void setup() {
        customerCompanyService = new CustomerCompanyServiceImpl(customerCompanyDao);
        customerCompanyOne = new CustomerCompany("Company One", "We make widgets", "Dominate the " +
                "widget market");
        customerCompanyTwo = new CustomerCompany("Company Two", "We make gadgets", "Number one " +
                "gadget maker in the world");
        customerCompanies = new ArrayList<>();
        customerCompanies.add(customerCompanyOne);
        customerCompanies.add(customerCompanyTwo);
    }

    @Test
    public void testSaveCustomerCompany() {
        given(this.customerCompanyDao.save(any(CustomerCompany.class))).willReturn(customerCompanyOne);
        CustomerCompany savedCustomerCompany = customerCompanyService.saveCustomerCompany(customerCompanyOne);

        Assert.assertEquals(customerCompanyOne, savedCustomerCompany);
    }

    @Test
    public void testGetAllCustomerCompanies() {
        given(this.customerCompanyDao.findAll()).willReturn(customerCompanies);
        List<CustomerCompany> companies = customerCompanyService.getAllCustomerCompanies();

        Assert.assertEquals(customerCompanies, companies);
    }

    @Test
    public void testGetCustomerCompanyById() {
        given(this.customerCompanyDao.findOne(any(Long.class))).willReturn(customerCompanyOne);
        CustomerCompany customerCompany = customerCompanyService.getCustomerCompanyById(1L);

        Assert.assertEquals(customerCompanyOne, customerCompany);
    }
}
