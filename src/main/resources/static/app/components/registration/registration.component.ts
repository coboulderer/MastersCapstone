import {Component, ViewChild} from "@angular/core";
import {RegisterService} from "../../services/register.service";
import {User} from "../../model/user";
import {Passwords} from "../../model/passwords";
import {NgForm} from "@angular/forms";


@Component({
    selector: "registration",
    templateUrl: "app/components/registration/registration.component.html"
})
export class Registration {

    newUser: User = new User();
    passwords: Passwords = new Passwords();

    registerSuccess: boolean = false;
    passwordsMatch: boolean = true;

    registerForm: NgForm;
    @ViewChild('registerForm') currentForm: NgForm;

    constructor(private registerService: RegisterService){}

    onSubmit() {
        console.log("Registration.onSubmit() function called");
        if (this.passwordsMatch) {
            this.newUser.password = this.passwords.password;
            this.registerService.registerUser(this.newUser);
            //TODO handle response if Username already exists or If new user created
        }
    }

    goBack() {
        console.log("Registration.goBack()");
        window.history.back();
    }

    ngAfterViewChecked() {
        this.formChanged();
    }

    formChanged() {
        console.log("form change detected");
        if (this.currentForm === this.registerForm) { return; }
        this.registerForm = this.currentForm;
        if (this.registerForm) {
            this.registerForm.valueChanges
                .subscribe(data => this.onValueChanged(data));
        }
    }

    onValueChanged(data?: any) {
        if (this.passwords.password != null &&
            this.passwords.password.length > 5 &&
            this.passwords.passwordConf != null &&
            this.passwords.passwordConf.length > 5) {

            if (this.passwords.password != this.passwords.passwordConf) {
                this.passwordsMatch = false;
            } else {
                this.passwordsMatch = true;
            }
        }
    }
}