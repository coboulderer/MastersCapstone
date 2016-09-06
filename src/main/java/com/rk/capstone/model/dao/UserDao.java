package com.rk.capstone.model.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rk.capstone.model.domain.User;

/**
 * Interface for User CRUD
 */
@Repository
public interface UserDao extends CrudRepository<User, Long> {

    User save(User user);
}
