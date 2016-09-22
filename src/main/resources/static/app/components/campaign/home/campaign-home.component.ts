import {Component} from "@angular/core";
import {CampaignService} from "../../../services/campaign.service";

@Component({
    selector: "campaign-home",
    templateUrl: "app/components/campaign/home/campaign-home.component.html",
    providers: [
        CampaignService
    ]
})
export class CampaignHome {

    constructor(private campaignService: CampaignService){}

    createNewCampaign() {
        console.log("CampaignHome.createNewCampaign() function called");
        // TODO - Implementation
    }

    addTask() {
        console.log("CampaignHome.addTask() function called");
        // TODO - Implementation
    }
}