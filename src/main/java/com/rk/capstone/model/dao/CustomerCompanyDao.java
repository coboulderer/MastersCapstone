package com.rk.capstone.model.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rk.capstone.model.domain.CustomerCompany;

/**
 * DAO for CustomerCompany Object
 */
@Repository
public interface CustomerCompanyDao extends CrudRepository<CustomerCompany, Long> {

    @Override
    List<CustomerCompany> findAll();
}
