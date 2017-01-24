package com.rk.capstone.model.services.customer.company;

import java.util.List;

import com.rk.capstone.model.domain.CustomerCompany;

/**
 * Service Interface for CustomerCompany
 */
public interface CustomerCompanyService {

    CustomerCompany saveCustomerCompany(CustomerCompany customerCompany);

    CustomerCompany getCustomerCompanyById(Long id);

    List<CustomerCompany> getAllCustomerCompanies();

    void deleteCustomerCompanyById(Long id);
}
