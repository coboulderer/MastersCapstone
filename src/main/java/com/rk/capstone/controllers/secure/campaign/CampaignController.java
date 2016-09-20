package com.rk.capstone.controllers.secure.campaign;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rk.capstone.model.domain.Campaign;
import com.rk.capstone.model.domain.User;
import com.rk.capstone.model.services.campaign.ICampaignService;
import com.rk.capstone.model.services.user.IUserService;

/**
 * REST Controller for /api/secure/campaign endpoints
 */
@RestController
@RequestMapping(value = "/api/secure/campaign")
public class CampaignController {

    private IUserService userService;
    private ICampaignService campaignService;

    public CampaignController(IUserService userService, ICampaignService campaignService) {
        this.userService = userService;
        this.campaignService = campaignService;
    }

    @RequestMapping(value = "/user/{userName}", method = RequestMethod.POST)
    public ResponseEntity<Campaign> createNewCampaign(@PathVariable String userName,
                                                      @RequestBody Campaign campaign) {
        ResponseEntity<Campaign> response;
        User user = userService.findByUserName(userName);
        if (campaign == null || campaign.getCampaignId() != null) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else if (user == null) {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            campaign.setOwner(user);
            campaign = campaignService.saveCampaign(campaign);
            response = ResponseEntity.status(HttpStatus.CREATED).body(campaign);
        }
        return response;
    }

    @RequestMapping(value = "/{campaignId}", method = RequestMethod.PUT)
    public ResponseEntity<Campaign> updateCampaign(@PathVariable Long campaignId,
                                                   @RequestBody Campaign campaign) {
        ResponseEntity<Campaign> response;
        if (campaignId == null || campaign == null || campaign.getCampaignId() == null ||
                !campaignId.equals(campaign.getCampaignId())) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            Campaign foundCampaign = campaignService.getCampaignById(campaignId);
            if (foundCampaign == null) {
                response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(campaign);
            } else {
                campaign.setOwner(foundCampaign.getOwner());
                campaign = campaignService.saveCampaign(campaign);
                response = ResponseEntity.status(HttpStatus.OK).body(campaign);
            }
        }
        return response;
    }

    @RequestMapping(value = "/{campaignId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteCampaign(@PathVariable Long campaignId) {
        ResponseEntity<String> response;
        if (campaignId == null) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else if (campaignService.getCampaignById(campaignId) == null) {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Campaign Not Found");
        } else {
            campaignService.deleteCampaignById(campaignId);
            response = ResponseEntity.status(HttpStatus.OK).body("Campaign Deleted - id = " +
                    campaignId);
        }
        return response;
    }

    @RequestMapping(value = "/{campaignId}", method = RequestMethod.GET)
    public ResponseEntity<Campaign> getCampaign(@PathVariable Long campaignId) {
        ResponseEntity<Campaign> response;
        if (campaignId == null) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            Campaign campaign = campaignService.getCampaignById(campaignId);
            if (campaign == null) {
                response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            } else {
                response = ResponseEntity.status(HttpStatus.OK).body(campaign);
            }
        }
        return response;
    }

    @RequestMapping(value = "/user/{userName}", method = RequestMethod.GET)
    public ResponseEntity<List<Campaign>> getAllUserCampaigns(@PathVariable String userName) {

        ResponseEntity<List<Campaign>> response;
        User user = userService.findByUserName(userName);
        if (userName == null || userName.isEmpty()) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else if (user == null) {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            List<Campaign> campaigns = campaignService.getOwnedCampaigns(user);
            response = ResponseEntity.status(HttpStatus.OK).body(campaigns);
        }
        return response;
    }
}
