package com.rk.capstone.model.services.customer.company;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rk.capstone.model.dao.CustomerCompanyDao;
import com.rk.capstone.model.domain.CustomerCompany;

/**
 * Implementation of ICustomerCompanyService
 */
@Service
public class CustomerCompanyServiceImpl implements ICustomerCompanyService{

    private final CustomerCompanyDao customerCompanyDao;

    public CustomerCompanyServiceImpl(CustomerCompanyDao customerCompanyDao) {
        this.customerCompanyDao = customerCompanyDao;
    }

    @Override
    public CustomerCompany saveCustomerCompany(CustomerCompany customerCompany) {
        return this.customerCompanyDao.save(customerCompany);
    }

    @Override
    public CustomerCompany getCustomerCompanyById(Long id) {
        return this.customerCompanyDao.findOne(id);
    }

    @Override
    public List<CustomerCompany> getAllCustomerCompanies() {
        return this.customerCompanyDao.findAll();
    }

    @Override
    public void deleteCustomerCompanyById(Long id) {
        this.customerCompanyDao.delete(id);
    }
}
