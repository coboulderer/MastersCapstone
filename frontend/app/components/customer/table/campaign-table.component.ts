import {Component, Input, OnChanges, SimpleChanges} from "@angular/core";
import {CustomerCompany} from "../../../model/customer-company";
import {Campaign} from "../../../model/campaign";
import {CustomerCompanyService} from "../../../services/data/customer-company.service";

@Component({
    selector:"campaign-table",
    templateUrl: "app/components/customer/table/campaign-table.component.html"
})
export class CampaignTable implements OnChanges {

    @Input() currentCompany:CustomerCompany;

    private campaigns:Campaign[];

    constructor(private customerCompanyService:CustomerCompanyService){
        this.campaigns = [];
    }

    ngOnChanges(changes:SimpleChanges):void {
        console.log("Changes detected by CampaignTable");
        this.loadCustomerCampaigns();
    }

    loadCustomerCampaigns() {
        console.log("Loading currentCompany Campaigns into data table")
        this.customerCompanyService.getAllCustomerCampaigns(this.currentCompany).subscribe(campaigns => {
            console.log("Campaigns Retrieved");
            this.campaigns = campaigns;
        },
        error => {
            console.log("Error Caught Retrieving customer campaigns");
            let errorMessage = <any>error;
            console.log("Error message:: " + errorMessage);
        });
    }
}