package com.rk.capstone.model.services.campaign;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rk.capstone.model.dao.CampaignDao;
import com.rk.capstone.model.domain.Campaign;
import com.rk.capstone.model.domain.User;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

/**
 * Test Class for Campaign Services
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class CampaignServiceTest {

    private CampaignService campaignService;
    private CampaignDao campaignDao = mock(CampaignDao.class);
    private Campaign campaignOne;
    private Campaign campaignTwo;
    private List<Campaign> campaigns;
    private User user;

    @Before
    public void setup() {
        campaignService = new CampaignServiceImpl(campaignDao);
        user = new User("firstname", "lastname", "email", "username", "abc123", null);
        campaignOne = new Campaign(1L, "nameOne", "commit", "sol summary", "pending", new Date(), new Date(), 1000, user);
        campaignTwo = new Campaign(2L, "nameTwo", "upside", "another summary", "closed", new Date(), new Date(), 2000, user);
        campaigns = new ArrayList<>();
        campaigns.add(campaignOne);
        campaigns.add(campaignTwo);
        user.setCampaigns(campaigns);
    }

    @Test
    public void testSaveCampaign() {
        given(this.campaignDao.save(any(Campaign.class))).willReturn(campaignOne);
        Campaign savedCampaign = campaignService.saveCampaign(campaignOne);

        Assert.assertEquals(campaignOne, savedCampaign);
    }

    @Test
    public void testGetOwnedCampaigns() {
        given(this.campaignDao.findByOwner(any(User.class))).willReturn(campaigns);
        List<Campaign> foundCampaigns = campaignService.getOwnedCampaigns(user);

        Assert.assertEquals(campaigns, foundCampaigns);
    }

    @Test
    public void testGetAllCustomerCampaigns() {
        given(this.campaignDao.findByCustomerId(any(Long.class))).willReturn(campaigns);
        List<Campaign> foundCampaigns = campaignService.getAllCustomerCampaigns(1L);

        Assert.assertEquals(campaigns, foundCampaigns);
    }

    @Test
    public void testGetOwnedCampaignsNoneFound() {
        given(this.campaignDao.findByOwner(any(User.class))).willReturn(null);
        List<Campaign> foundCampaigns = campaignService.getOwnedCampaigns(user);

        Assert.assertNull(foundCampaigns);
    }

    @Test
    public void testGetCampaignById() {
        given(this.campaignDao.findByCampaignId(any(Long.class))).willReturn(campaignOne);
        Campaign foundCampaign = campaignService.getCampaignById(1L);

        Assert.assertEquals(campaignOne, foundCampaign);
    }

    @Test
    public void testGetCampaignByIdNotFound() {
        given(this.campaignDao.findByCampaignId(any(Long.class))).willReturn(null);
        Campaign foundCampaign = campaignService.getCampaignById(1L);

        Assert.assertNull(foundCampaign);
    }
}
