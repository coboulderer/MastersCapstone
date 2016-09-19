package com.rk.capstone.controllers.secure.campaign;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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

    @RequestMapping(value = "/{userName}", method = RequestMethod.GET)
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
