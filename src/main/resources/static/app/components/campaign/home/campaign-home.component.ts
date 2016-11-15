import {Component, OnInit, ViewChild, ElementRef} from "@angular/core";
import {CampaignService} from "../../../services/data/campaign.service";
import {CampaignTaskService} from "../../../services/data/campaign-task.service";
import {Campaign} from "../../../model/campaign";
import {CampaignNew} from "../new/campaign-new.component";
import {DateService} from "../../../services/util/date.service";
import {CustomerCompanyService} from "../../../services/data/customer-company.service";
import {CustomerCompany} from "../../../model/customer-company";

declare var jQuery: any;

@Component({
    selector: "campaign-home",
    templateUrl: "app/components/campaign/home/campaign-home.component.html",
    providers: [
        CampaignService,
        CampaignTaskService,
        CustomerCompanyService,
        DateService
    ]
})
export class CampaignHome implements OnInit{

    @ViewChild("deleteModal") private modal: ElementRef;
    @ViewChild(CampaignNew) private campaignNew: CampaignNew;

    private currentCampaign: Campaign;
    private allUserCampaigns: Campaign[];
    private allCustomerCompanies: CustomerCompany[];

    constructor(private campaignService: CampaignService,
                private campaignTaskService: CampaignTaskService,
                private customerCompanyService: CustomerCompanyService,
                private dateService: DateService){
        this.allUserCampaigns = [];
        this.allCustomerCompanies = [];
    }

    ngOnInit():void {
        console.log("Init CampaignHome Component and checking for existing campaigns");
        this.loadInitialData();
    }

    addCreatedCampaign(campaign: Campaign) {
        console.log("CampaignHome.addCreatedCampaign(Campaign) function called");
        this.updateCampaign(campaign);
        this.allUserCampaigns.push(campaign);
    }

    updateCampaign(campaign: Campaign){
        console.log("CampaignHome.updateCampaign");
        this.currentCampaign = campaign;
    }

    showDeleteModal(data?: {}) {
        console.log("CampaignHome.showDeleteModal() Called");
        jQuery(this.modal.nativeElement)
            .modal(data || {})
            .modal("toggle");
    }

    editCurrentCampaign() {
        console.log("CampaignHome.editCurrentCampaign()");
        this.campaignNew.update(this.currentCampaign);
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
                        this.loadInitialData();
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

    private loadInitialData() {
        console.log("CampaignHome.loadCampaigns()");
        this.customerCompanyService.getAllCustomerCompanies().subscribe(companies => {
                console.log("Found Customer Companies");
                if (companies.length > 0) {
                    this.allCustomerCompanies = companies;
                }
            },
            error => {
                console.log("Error Caught in CampaignHome.loadCampaigns()");
                console.log("Error Message:\n" + error);
            });
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