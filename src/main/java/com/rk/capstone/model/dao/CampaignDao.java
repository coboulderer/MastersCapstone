package com.rk.capstone.model.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rk.capstone.model.domain.Campaign;

/**
 * DAO for Campaigns
 */
@Repository
public interface CampaignDao extends CrudRepository<Campaign, Long> {

    // TODO - IMPLEMENT

}
