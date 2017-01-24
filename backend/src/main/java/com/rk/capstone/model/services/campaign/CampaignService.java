package com.rk.capstone.model.services.campaign;

import java.util.List;

import com.rk.capstone.model.domain.Campaign;
import com.rk.capstone.model.domain.User;

/**
 * Service Interface for Campaigns
 */
public interface CampaignService {

    Campaign saveCampaign(Campaign campaign);

    List<Campaign> getOwnedCampaigns(User owner);

    List<Campaign> getAllCustomerCampaigns(Long customerId);

    Campaign getCampaignById(Long campaignId);

    void deleteCampaignById(Long campaignId);
}
