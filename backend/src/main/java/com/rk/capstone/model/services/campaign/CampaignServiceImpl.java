package com.rk.capstone.model.services.campaign;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rk.capstone.model.dao.CampaignDao;
import com.rk.capstone.model.domain.Campaign;
import com.rk.capstone.model.domain.User;

/**
 * Implementation of ICampaignService
 */
@Service
public class CampaignServiceImpl implements CampaignService {

    private final CampaignDao campaignDao;

    public CampaignServiceImpl(CampaignDao campaignDao) {
        this.campaignDao = campaignDao;
    }

    @Override
    public Campaign saveCampaign(Campaign campaign) {
        return campaignDao.save(campaign);
    }

    @Override
    public List<Campaign> getAllUserCampaigns(User user) {
        return campaignDao.findCampaignsByUser(user);
    }

    @Override
    public List<Campaign> getAllCustomerCampaigns(Long customerId) {
        return campaignDao.findCampaignsByCustomerId(customerId);
    }

    @Override
    public Campaign getCampaignById(Long campaignId) {
        return campaignDao.findOne(campaignId);
    }

    @Override
    public void deleteCampaignById(Long campaignId) {
        campaignDao.delete(campaignId);
    }
}
