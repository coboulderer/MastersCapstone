import {Component, ElementRef, ViewChild} from "@angular/core";
import {CustomerCompanyService} from "../../../services/data/customer-company.service";
import {CustomerCompany} from "../../../model/customer-company";
import {FormGroup, FormControl} from "@angular/forms";

declare var jQuery: any;

@Component({
    selector: "customer-new",
    templateUrl: "app/components/customer/new/customer-new.component.html",
    providers: [
        CustomerCompanyService
    ]
})

export class CustomerNew {

    @ViewChild("modal") modal: ElementRef;

    private form = new FormGroup({
        customerName        : new FormControl(),
        customerSummary     : new FormControl(),
        customerInitiative  : new FormControl()
    });

    private newCustomerCompany: CustomerCompany = new CustomerCompany();

    constructor(private customerService: CustomerCompanyService){}

    show(data?: {}){
        console.log("CustomerNew.show() Called");
        if (this.newCustomerCompany.id == null){
            this.form.reset();
        }
        jQuery(this.modal.nativeElement)
            .modal(data || {})
            .modal("toggle");
    }

    hide() {
        console.log("CustomerNew.hide() Called");
        this.newCustomerCompany = new CustomerCompany();
        jQuery(this.modal.nativeElement)
            .modal("hide");
    }

    update() {
        console.log("CustomerNew.update() called");
    }

    save() {
        console.log("CustomerNew.save() called");
    }

}