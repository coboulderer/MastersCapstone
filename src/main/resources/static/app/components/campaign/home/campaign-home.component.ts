import {Component, OnInit} from "@angular/core";
import {CampaignService} from "../../../services/campaign.service";
import {Campaign} from "../../../model/campaign";

@Component({
    selector: "campaign-home",
    templateUrl: "app/components/campaign/home/campaign-home.component.html",
    providers: [
        CampaignService
    ]
})
export class CampaignHome implements OnInit{

    private currentCampaign: Campaign;
    private customerCampaigns: Campaign[];

    constructor(private campaignService: CampaignService){}

    ngOnInit():void {
        this.customerCampaigns = [];
        // TODO - Get All User Campaigns
        // TODO - Display first one
    }

    campaignCreated(campaign: Campaign) {
        console.log("CampaignHome.createNewCampaign() function called");
        this.currentCampaign = campaign;
        //this.customerCampaigns.push(campaign);
    }

    addTask() {
        console.log("CampaignHome.addTask() function called");
        // TODO - Implementation
    }
}