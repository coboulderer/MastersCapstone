package com.rk.capstone.controllers.secure.customer.company;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rk.capstone.model.domain.Campaign;
import com.rk.capstone.model.domain.CustomerCompany;
import com.rk.capstone.model.services.campaign.ICampaignService;
import com.rk.capstone.model.services.customer.company.ICustomerCompanyService;

/**
 * REST Controller for /api/secure/customer/company endpoints
 */
@RestController
@RequestMapping(value = "/api/secure/customer/company")
public class CustomerCompanyController {

    private ICustomerCompanyService customerCompanyService;
    private ICampaignService campaignService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public CustomerCompanyController(ICustomerCompanyService customerCompanyService,
                                     ICampaignService campaignService) {
        this.customerCompanyService = customerCompanyService;
        this.campaignService = campaignService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<CustomerCompany>> getAllCustomerCompanies() {
        ResponseEntity<List<CustomerCompany>> response;
        List<CustomerCompany> customerCompanies = customerCompanyService.getAllCustomerCompanies();
        logger.info("Attempting to get all customer companies");
        if (customerCompanies == null || customerCompanies.isEmpty()) {
            logger.info("No customer companies found - returning no content");
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } else {
            logger.info("Returning " + customerCompanies.size() + " customer companies");
            response = ResponseEntity.status(HttpStatus.OK).body(customerCompanies);
        }
        return response;
    }

    @RequestMapping(value = "/{id}/campaigns", method = RequestMethod.GET)
    public ResponseEntity<List<Campaign>> getCustomerCampaigns(@PathVariable long id) {
        ResponseEntity<List<Campaign>> response;
        CustomerCompany customerCompany = customerCompanyService.getCustomerCompanyById(id);
        logger.info("Attempting to get all customer campaigns");
        if (customerCompany == null) {
            logger.error("Customer company with id = " + id + " not found, cannot return campaigns");
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            List<Campaign> campaigns = this.campaignService.getAllCustomerCampaigns(id);
            logger.info("Returning " + campaigns.size() + " customer campaigns");
            response = ResponseEntity.status(HttpStatus.OK).body(campaigns);
        }
        return response;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CustomerCompany> createNewCustomerCompany(
            @RequestBody CustomerCompany customerCompany) {
        ResponseEntity<CustomerCompany> response;
        customerCompany = customerCompanyService.saveCustomerCompany(customerCompany);
        logger.info("Attempting to create a new customer company");
        if (customerCompany == null || customerCompany.getId() == 0L) {
            logger.error("Cannot create a null or preexisting customer company");
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } else {
            logger.info("Creating new customer company");
            response = ResponseEntity.status(HttpStatus.CREATED).body(customerCompany);
        }
        return response;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteCustomerCompany(@PathVariable long id) {
        ResponseEntity<String> response;
        CustomerCompany customerCompany = customerCompanyService.getCustomerCompanyById(id);
        logger.info("Attempting to delete customer company with id = " + id);
        if (customerCompany == null) {
            logger.error("Customer company with id = " + id + " not found, delete failed");
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("The Customer Company you " +
                    "requested was not found.");
        } else {
            logger.info("Found customer company with id = " + id + ", deleting");
            customerCompanyService.deleteCustomerCompanyById(id);
            response = ResponseEntity.status(HttpStatus.OK).body("Customer Company Deleted");
        }
        return response;
    }
}
