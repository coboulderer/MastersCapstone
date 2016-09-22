import {Component, ViewChild, ElementRef} from "@angular/core";
import {FormControl, FormGroup} from "@angular/forms";
import {CampaignService} from "../../../services/campaign.service";

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

    private form = new FormGroup({
        revenue         : new FormControl(),
        campaignStrength: new FormControl(),
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
        console.log("CampaignNew.save() Called")
    }
}