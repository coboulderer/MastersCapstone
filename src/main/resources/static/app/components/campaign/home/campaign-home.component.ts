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
    private allUserCampaigns: Campaign[];

    constructor(private campaignService: CampaignService){
        this.allUserCampaigns = [];
    }

    ngOnInit():void {
        console.log("Init CampaignHome Component and checking for existing campaigns");
        this.loadCampaigns();
    }

    campaignCreated(campaign: Campaign) {
        console.log("CampaignHome.campaignCreated() function called");
        this.currentCampaign = campaign;
        this.allUserCampaigns.push(campaign);
    }

    addTask() {
        console.log("CampaignHome.addTask() function called");
        // TODO - Implementation
    }

    private loadCampaigns() {
        this.campaignService.getAllUserCampaigns().subscribe(campaigns => {
                console.log("Found User Campaigns");
                if (campaigns.length > 0) {
                    this.allUserCampaigns = campaigns;
                    this.currentCampaign = this.allUserCampaigns[0];
                }
            },
            error => {
                console.log("Error Caught in CampaignHome.loadCampaigns()");
                console.log("Error Message:\n" + error);
            });
    }
}