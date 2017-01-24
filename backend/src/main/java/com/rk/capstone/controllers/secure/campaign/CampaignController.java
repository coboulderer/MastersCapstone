package com.rk.capstone.controllers.secure.campaign;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rk.capstone.model.domain.Campaign;
import com.rk.capstone.model.domain.User;
import com.rk.capstone.model.services.campaign.CampaignService;
import com.rk.capstone.model.services.user.UserService;

/**
 * REST Controller for /api/secure/campaign endpoints
 */
@RestController
@RequestMapping(value = "/api/secure/campaign")
public class CampaignController {

    private UserService userService;
    private CampaignService campaignService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public CampaignController(UserService userService, CampaignService campaignService) {
        this.userService = userService;
        this.campaignService = campaignService;
    }

    @RequestMapping(value = "/user/{userName}", method = RequestMethod.POST)
    public ResponseEntity<Campaign> createNewCampaign(@PathVariable String userName,
                                                      @RequestBody Campaign campaign) {
        ResponseEntity<Campaign> response;
        User user = userService.findByUserName(userName);
        logger.info("Attempting to create a new campaign");
        if (campaign == null || campaign.getCampaignId() != null) {
            logger.error("Campaign creation failed - the provided campaign is null or already has" +
                    " an id");
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else if (user == null) {
            logger.error("Null users cannot create a campaign");
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            logger.info("Creating New Campaign");
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
        logger.info("Attempting to update the provided campaign");
        if (campaignId == null || campaign == null || campaign.getCampaignId() == null ||
                !campaignId.equals(campaign.getCampaignId())) {
            logger.error("Cannot update null objects");
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            Campaign foundCampaign = campaignService.getCampaignById(campaignId);
            if (foundCampaign == null) {
                logger.error("The provided campaign was not found");
                logger.error(campaign.toString());
                response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(campaign);
            } else {
                logger.info("Updating");
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
        logger.info("Attempting to delete the provided campaign");
        if (campaignId == null) {
            logger.error("Cannot delete a campaign with a null id");
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else if (campaignService.getCampaignById(campaignId) == null) {
            logger.error("Could not find the desired campaignId: " + campaignId);
            logger.error("Delete failed");
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Campaign Not Found");
        } else {
            logger.info("Deleting campaign with id = " + campaignId);
            campaignService.deleteCampaignById(campaignId);
            response = ResponseEntity.status(HttpStatus.OK).body("Campaign Deleted - id = " +
                    campaignId);
        }
        return response;
    }

    @RequestMapping(value = "/{campaignId}", method = RequestMethod.GET)
    public ResponseEntity<Campaign> getCampaign(@PathVariable Long campaignId) {
        ResponseEntity<Campaign> response;
        logger.info("Attempting to retrieve specified campaignId: " + campaignId);
        if (campaignId == null) {
            logger.error("Failed - cannot get a null campaignId");
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            Campaign campaign = campaignService.getCampaignById(campaignId);
            if (campaign == null) {
                logger.error("The requested campaignId: " + campaignId + " was not found");
                response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            } else {
                logger.info("Campaign found - returning campaignId: " + campaignId);
                response = ResponseEntity.status(HttpStatus.OK).body(campaign);
            }
        }
        return response;
    }

    @RequestMapping(value = "/user/{userName}", method = RequestMethod.GET)
    public ResponseEntity<List<Campaign>> getAllUserCampaigns(@PathVariable String userName) {
        ResponseEntity<List<Campaign>> response;
        User user = userService.findByUserName(userName);
        logger.info("Attempting to retrieve all user campaigns");
        if (userName == null || userName.isEmpty()) {
            logger.error("Cannot get campaigns of a null or empty username");
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else if (user == null) {
            logger.error("The provided userName: " + userName + " was not found");
            logger.error("Campaign retrieval failed");
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            List<Campaign> campaigns = campaignService.getOwnedCampaigns(user);
            response = ResponseEntity.status(HttpStatus.OK).body(campaigns);
            logger.info("Returning all user campaigns, " + campaigns.size() + " campaigns found");
        }
        return response;
    }
}
