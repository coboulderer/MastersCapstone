package com.rk.capstone.controllers.secure.customer.company;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rk.capstone.model.domain.CustomerCompany;
import com.rk.capstone.model.services.customer.company.ICustomerCompanyService;

/**
 * REST Controller for /api/secure/customer/company endpoints
 */
@RestController
@RequestMapping(value = "/api/secure/customer/company")
public class CustomerCompanyController {

    private ICustomerCompanyService customerCompanyService;

    public CustomerCompanyController(ICustomerCompanyService customerCompanyService) {
        this.customerCompanyService = customerCompanyService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<CustomerCompany>> getAllCustomerCompanies() {
        ResponseEntity<List<CustomerCompany>> response;
        List<CustomerCompany> customerCompanies = customerCompanyService.getAllCustomerCompanies();
        if (customerCompanies == null || customerCompanies.isEmpty()) {
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } else {
            response = ResponseEntity.status(HttpStatus.OK).body(customerCompanies);
        }
        return response;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CustomerCompany> createNewCustomerCompany(
            @RequestBody CustomerCompany customerCompany) {
        ResponseEntity<CustomerCompany> response;
        customerCompany = customerCompanyService.saveCustomerCompany(customerCompany);
        if (customerCompany == null || customerCompany.getId() == 0L) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } else {
            response = ResponseEntity.status(HttpStatus.CREATED).body(customerCompany);
        }
        return response;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteCustomerCompany(@PathVariable long id) {
        ResponseEntity<String> response;
        CustomerCompany customerCompany = customerCompanyService.getCustomerCompanyById(id);
        if (customerCompany == null) {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("The Customer Company you " +
                    "requested was not found.");
        } else {
            customerCompanyService.deleteCustomerCompanyById(id);
            response = ResponseEntity.status(HttpStatus.OK).body("Customer Company Deleted");
        }
        return response;
    }
}
