package com.rk.capstone.model.services.customer.company;

import java.util.List;

import com.rk.capstone.model.domain.CustomerCompany;

/**
 * Service Interface for CustomerCompany
 */
public interface ICustomerCompanyService {

    CustomerCompany saveCustomerCompany(CustomerCompany customerCompany);

    List<CustomerCompany> getAllCustomerCompanies();

    void deleteCustomerCompanyById(Long id);
}
