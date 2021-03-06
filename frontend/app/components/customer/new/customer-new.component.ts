import {Component, ElementRef, ViewChild, Output, EventEmitter} from "@angular/core";
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
    @Output() customerCompanyEmitter = new EventEmitter<CustomerCompany>();

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
        jQuery(this.modal.nativeElement).modal(data || {}).modal("toggle");
    }

    hide() {
        console.log("CustomerNew.hide() Called");
        this.newCustomerCompany = new CustomerCompany();
        jQuery(this.modal.nativeElement).modal("hide");
    }

    update() {
        console.log("CustomerNew.update() called");
    }

    save() {
        console.log("CustomerNew.save() called");
        this.customerService.createNewCustomerCompany(this.newCustomerCompany).
            subscribe(customerCompany => {
                console.log("New CustomerCompany Created");
                this.customerCompanyEmitter.emit(customerCompany);
                this.hide();
            },
            error => {
                console.log("Error caught creating new customer saving");
                let errorMessage = <any>error;
                console.log("Error Message:\n" + errorMessage);
            });
    }
}