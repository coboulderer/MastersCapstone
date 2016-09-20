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
public class CampaignServiceImpl implements ICampaignService {

    private final CampaignDao campaignDao;

    public CampaignServiceImpl(CampaignDao campaignDao) {
        this.campaignDao = campaignDao;
    }

    @Override
    public Campaign saveCampaign(Campaign campaign) {
        return campaignDao.save(campaign);
    }

    @Override
    public List<Campaign> getOwnedCampaigns(User owner) {
        return campaignDao.findByOwner(owner);
    }

    @Override
    public Campaign getCampaignById(Long campaignId) {
        return campaignDao.findByCampaignId(campaignId);
    }

    @Override
    public void deleteCampaignById(Long id) {
        campaignDao.delete(id);
    }
}
