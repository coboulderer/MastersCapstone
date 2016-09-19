package com.rk.capstone.model.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rk.capstone.model.domain.Campaign;
import com.rk.capstone.model.domain.User;

/**
 * DAO for Campaigns
 */
@Repository
public interface CampaignDao extends CrudRepository<Campaign, Long> {

    Campaign save(Campaign campaign);

    List<Campaign> findByOwner(User owner);

    Campaign findByCampaignId(Long campaignId);
}
