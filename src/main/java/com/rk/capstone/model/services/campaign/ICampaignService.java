package com.rk.capstone.model.services.campaign;

import java.util.List;

import com.rk.capstone.model.domain.Campaign;
import com.rk.capstone.model.domain.User;

/**
 * Service Interface for Campaigns
 */
public interface ICampaignService {

    Campaign saveCampaign(Campaign campaign);

    List<Campaign> getOwnedCampaigns(User owner);

    Campaign getCampaignById(Long id);
}
