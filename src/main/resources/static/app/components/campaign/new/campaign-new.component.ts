import {Component, ViewChild, ElementRef, Output, EventEmitter} from "@angular/core";
import {FormControl, FormGroup} from "@angular/forms";
import {CampaignService} from "../../../services/campaign.service";
import {Campaign} from "../../../model/campaign";

declare var jQuery: any;

@Component({
    selector: "campaign-new",
    templateUrl: "app/components/campaign/new/campaign-new.component.html",
    providers: [
        CampaignService
    ]
})

export class CampaignNew {

    @ViewChild("modal") modal: ElementRef;
    @Output() campaignCreated = new EventEmitter<Campaign>();

    private newCampaign: Campaign = new Campaign();

    private form = new FormGroup({
        campaignName    : new FormControl(),
        revenue         : new FormControl(),
        campaignStrength: new FormControl(),
        closeStatus     : new FormControl(),
        startDate       : new FormControl(),
        endDate         : new FormControl(),
        solutionSummary : new FormControl()
    });

    constructor(private campaignService: CampaignService){}

    show(data?: {}) {
        console.log("CampaignNew.show() Called");
        jQuery(this.modal.nativeElement)
            .modal(data || {})
            .modal("toggle");
    }

    hide() {
        console.log("CampaignNew.hide() Called");
        this.form.reset();
        jQuery(this.modal.nativeElement)
            .modal("hide");
    }

    save() {
        console.log("CampaignNew.save() Called");
        this.campaignService.createNewCampaign(this.newCampaign).subscribe(campaign => {
                console.log("New Campaign Created");
                this.campaignCreated.emit(campaign);
                this.hide();
            },
            error => {
                console.log("Error caught in CampaignService.save()");
                let errorMessage = <any>error;
                console.log("Error Message:\n" + errorMessage);
            });
    }
}