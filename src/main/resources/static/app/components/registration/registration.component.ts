import {Component} from "@angular/core";
import{User} from "../../model/user";
import {RegisterService} from "../../services/register.service";


@Component({
    selector: "registration",
    templateUrl: "app/components/registration/registration.component.html"
})
export class Registration {

    newUser: User = new User();
    registerSuccess: boolean = false;

    constructor(private registerService: RegisterService){}

    onSubmit() {
        console.log("Registration.onSubmit() function called");
        this.registerService.registerUser(this.newUser);

    }

    goBack() {
        console.log("Registration.goBack()");
        window.history.back();
    }
}