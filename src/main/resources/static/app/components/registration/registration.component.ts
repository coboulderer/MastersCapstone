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

    errorMessage: string;

    submitError: boolean = false;
    registerSuccess: boolean = false;
    passwordsMatch: boolean = true;

    registerForm: NgForm;
    @ViewChild('registerForm') currentForm: NgForm;

    constructor(private registerService: RegisterService,
                private passwordValidator: PasswordValidationService) {
    }

    onSubmit() {
        console.log("Registration.onSubmit() function called");
        if (this.passwordsMatch) {
            this.newUser.password = this.passwords.password;
            this.registerService.registerUser(this.newUser).subscribe(users => {
                    // JSON.stringify(users);
                    console.log("Users JSON = " + JSON.stringify(users));
                    //TODO - Finishe Parsing response
                    this.submitError = false;
                },
                error => {
                    console.log("Error caught in RegistrationComponent.onSubmit()");
                    this.errorMessage = <any>error;
                    this.submitError = true;
                    console.log("Error Message:\n" + this.errorMessage);
                });
        }
    }

    ngAfterViewChecked() {
        this.formChanged();
    }

    formChanged() {
        if (this.currentForm === this.registerForm) {
            return;
        }
        this.registerForm = this.currentForm;
        if (this.registerForm) {
            this.registerForm.valueChanges.subscribe(() => {
                this.passwordsMatch = this.passwordValidator.checkMatching(this.passwords);
                //TODO Check Password Complexity - Low priority for now
            });
        }
    }
}