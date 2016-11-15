import {Component, ViewChild, ElementRef, Output, EventEmitter, OnInit} from "@angular/core";
import {FormControl, FormGroup} from "@angular/forms";
import {CampaignService} from "../../../services/data/campaign.service";
import {Campaign} from "../../../model/campaign";
import {DateService} from "../../../services/util/date.service";
import {CustomerCompanyService} from "../../../services/data/customer-company.service";
import {CustomerCompany} from "../../../model/customer-company";

declare var jQuery: any;

@Component({
    selector: "campaign-new",
    templateUrl: "app/components/campaign/new/campaign-new.component.html",
    providers: [
        CampaignService,
        CustomerCompanyService,
        DateService
    ]
})

export class CampaignNew implements OnInit {
    @ViewChild("modal") modal: ElementRef;

    @Output() campaignCreated = new EventEmitter<Campaign>();
    @Output() campaignUpdated = new EventEmitter<Campaign>();
    private newCampaign: Campaign = new Campaign();
    private customerCompanies:CustomerCompany[];

    private existingStartDate: Date = null;
    private existingEndDate: Date = null;
    private form = new FormGroup({
        campaignName    : new FormControl(),
        revenue         : new FormControl(),
        campaignStrength: new FormControl(),
        closeStatus     : new FormControl(),
        startDate       : new FormControl(),
        newStartDate    : new FormControl(),
        endDate         : new FormControl(),
        newEndDate      : new FormControl(),
        solutionSummary : new FormControl(),
        company         : new FormControl()
    });

    constructor(private campaignService: CampaignService, private customerCompanyService:CustomerCompanyService,
                private dateService: DateService){
        this.customerCompanies = []
    }

    ngOnInit(): void {
        this.loadCompanies();
    }

    show(data?: {}) {
        console.log("CampaignNew.show() Called");
        if (this.newCampaign.campaignId == null){
            this.form.reset();
        }
        jQuery(this.modal.nativeElement)
            .modal(data || {})
            .modal("toggle");
    }

    hide() {
        console.log("CampaignNew.hide() Called");
        this.newCampaign = new Campaign();
        jQuery(this.modal.nativeElement)
            .modal("hide");
    }

    update(campaign: Campaign) {
        console.log("CampaignNew.update()");
        this.existingStartDate = this.dateService.toUtcDate(campaign.startDate);
        this.existingEndDate = this.dateService.toUtcDate(campaign.closeDate);
        this.newCampaign = campaign;
        this.show();
    }

    save() {
        if (this.newCampaign.campaignId == null){
            console.log("CampaignNew.save() CREATING Called");
            this.campaignService.createNewCampaign(this.newCampaign).subscribe(campaign => {
                    console.log("New Campaign Created");
                    this.campaignCreated.emit(campaign);
                    this.hide();
                },
                error => {
                    console.log("Error caught initially saving");
                    let errorMessage = <any>error;
                    console.log("Error Message:\n" + errorMessage);
                });
        } else {
            console.log("CampaignNew.save() Update Called");
            this.campaignService.updateCampaign(this.newCampaign).subscribe(campaign => {
                    console.log("Campaign Updated");
                    this.campaignUpdated.emit(campaign);
                    this.hide();
                },
                error => {
                    console.log("Error caught updating)");
                    let errorMessage = <any>error;
                    console.log("Error Message:\n" + errorMessage);
                });
        }
    }

    private loadCompanies() {
        console.log("CampaignNew.loadCompanies()");
        this.customerCompanyService.getAllCustomerCompanies().subscribe(companies => {
                console.log("Found Customer Companies");
                if (companies.length > 0) {
                    this.customerCompanies = companies;
                }
            },
            error => {
                console.log("Error Caught in CampaignHome.loadCampaigns()");
                console.log("Error Message:\n" + error);
            });
    }
}