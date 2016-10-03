import {Component, OnInit, ViewChild, ElementRef} from "@angular/core";
import {CampaignService} from "../../../services/campaign.service";
import {CampaignTaskService} from "../../../services/campaign-task.service";
import {Campaign} from "../../../model/campaign";

declare var jQuery: any;

@Component({
    selector: "campaign-home",
    templateUrl: "app/components/campaign/home/campaign-home.component.html",
    providers: [
        CampaignService,
        CampaignTaskService
    ]
})
export class CampaignHome implements OnInit{

    @ViewChild("deleteModal") modal: ElementRef;

    private currentCampaign: Campaign;
    private allUserCampaigns: Campaign[];

    constructor(private campaignService: CampaignService,
                private campaignTaskService: CampaignTaskService){
        this.allUserCampaigns = [];
    }

    ngOnInit():void {
        console.log("Init CampaignHome Component and checking for existing campaigns");
        this.loadCampaigns();
    }

    addCreatedCampaign(campaign: Campaign) {
        console.log("CampaignHome.addCreatedCampaign(Campaign) function called");
        this.currentCampaign = campaign;
        this.allUserCampaigns.push(campaign);
    }

    showDeleteModal(data?: {}) {
        console.log("CampaignHome.showDeleteModal() Called");
        jQuery(this.modal.nativeElement)
            .modal(data || {})
            .modal("toggle");
    }

    deleteCurrentCampaign() {
        console.log("CampaignHome.deleteCurrentCampaign() function called");
        this.campaignTaskService.deleteAllCampaignTasks(this.currentCampaign.campaignId).
        subscribe(response => {
                console.log(response);
                this.campaignService.deleteCampaign(this.currentCampaign.campaignId).
                subscribe(response => {
                        console.log(response);
                        this.allUserCampaigns = [];
                        this.currentCampaign = null;
                        this.loadCampaigns();
                    },
                    error => {
                        console.log("Error Caught in Deleting Campaign");
                        console.log("Error Message:\n" + error);
                    });
            },
            error => {
                console.log("Error Caught in Deleting Campaign Tasks");
                console.log("Error Message:\n" + error);
            });
    }

    loadSelected(campaign: Campaign) {
        console.log("CampaignHome.loadSelected(Campaign) function called");
        this.currentCampaign = campaign;
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