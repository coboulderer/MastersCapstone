import {Component, OnInit, ViewChild} from "@angular/core";
import {CustomerCompany} from "../../../model/customer-company";
import {CustomerCompanyService} from "../../../services/data/customer-company.service";
import {CustomerNew} from "../new/customer-new.component";

@Component({
    selector: "customer-home",
    templateUrl: "app/components/customer/home/customer-home.component.html",
    providers:[
        CustomerCompanyService
    ]
})

export class CustomerHome implements OnInit{

    @ViewChild(CustomerNew) private customerNew:CustomerNew;

    private currentCustomerCompany:CustomerCompany;
    private allCustomerCompanies:CustomerCompany[];

    constructor(private customerCompanyService: CustomerCompanyService){
        this.allCustomerCompanies = [];
    }

    ngOnInit():void {
        console.log("Init CustomerHome Component - Initial check for Customer Companies");
        this.loadCustomerCompanies();
    }

    private addCreatedCustomerCompany(customerCompany: CustomerCompany) {
        console.log("CustomerHome.addCreatedCustomerCompany function called");
        this.currentCustomerCompany = customerCompany;
        this.allCustomerCompanies.push(customerCompany);
    }

    private loadSelected(customerCompany: CustomerCompany) {
        console.log("CustomerHome.loadSelected called");
        this.currentCustomerCompany = customerCompany;
    }

    private loadCustomerCompanies() {
        console.log("CustomerHome.loadCustomerCompanies");
        this.customerCompanyService.getAllCustomerCompanies().subscribe(customerCompanies => {
            console.log("Found Customer Companies");
            if (customerCompanies.length > 0) {
                this.allCustomerCompanies = customerCompanies;
                this.currentCustomerCompany = customerCompanies[0];
            }
        }, error => {
            console.log("Error Caught in CustomerHome.loadCustomerCompanies");
            console.log("Error Message:\n" + error);
        });
    }
}