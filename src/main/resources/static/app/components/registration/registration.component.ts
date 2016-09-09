import {Component, ViewChild} from "@angular/core";
import {NgForm} from "@angular/forms";
import {User} from "../../model/user";
import {Passwords} from "../../model/passwords";
import {RegisterService} from "../../services/register.service";
import {PasswordValidationService} from "../../services/password-validation.service";


@Component({
    selector: "registration",
    templateUrl: "app/components/registration/registration.component.html",
    providers: [
        RegisterService,
        PasswordValidationService
    ]
})
export class Registration {

    newUser: User = new User();
    passwords: Passwords = new Passwords();

    registerSuccess: boolean = false;
    passwordsMatch: boolean = true;

    registerForm: NgForm;
    @ViewChild('registerForm') currentForm: NgForm;

    constructor(private registerService: RegisterService,
                private passwordValidator: PasswordValidationService){}

    onSubmit() {
        console.log("Registration.onSubmit() function called");
        if (this.passwordsMatch) {
            this.newUser.password = this.passwords.password;
            this.registerService.registerUser(this.newUser);
            //TODO handle response if Username already exists or If new user created
        }
    }

    ngAfterViewChecked() {
        this.formChanged();
    }

    formChanged() {
        if (this.currentForm === this.registerForm) { return; }
        this.registerForm = this.currentForm;
        if (this.registerForm) {
            this.registerForm.valueChanges.subscribe(() => {
                this.passwordsMatch = this.passwordValidator.checkMatching(this.passwords);
                //TODO Check Password Complexity - Low priority for now
            });
        }
    }
}