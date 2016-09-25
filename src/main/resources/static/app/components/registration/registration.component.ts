import {Component, ViewChild} from "@angular/core";
import {Router} from "@angular/router";
import {NgForm} from "@angular/forms";
import {User} from "../../model/user";
import {Passwords} from "../../model/passwords";
import {RegisterService} from "../../services/register.service";
import {PasswordValidationService} from "../../services/password-validation.service";
import {LoginService} from "../../services/login.service";


@Component({
    selector: "registration",
    templateUrl: "app/components/registration/registration.component.html",
    providers: [
        RegisterService,
        PasswordValidationService,
        LoginService
    ]
})
export class Registration {

    private newUser: User = new User();
    private passwords: Passwords = new Passwords();

    private errorMessage: string;

    private submitError: boolean = false;
    private registerSuccess: boolean = false;
    private passwordsMatch: boolean = true;

    private registerForm: NgForm;
    @ViewChild('registerForm') private currentForm: NgForm;

    constructor(private registerService: RegisterService,
                private passwordValidator: PasswordValidationService,
                private loginService: LoginService,
                private router: Router) {}

    onSubmit() {
        console.log("Registration.onSubmit() function called");
        if (this.passwordsMatch) {
            this.newUser.password = this.passwords.password;
            this.registerService.registerUser(this.newUser).subscribe(user => {
                    console.log("New User Registered - Getting ready to redirect to home page");
                    this.loginNewUser();
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

    private loginNewUser() {
        let credentials = {"username": "", "password": ""};
        credentials.username = this.newUser.userName;
        credentials.password = this.newUser.password;
        this.loginService.login(credentials).subscribe(response => {
                console.log("Login Successful");
                this.loginService.parseLoginResponse(response, credentials);
                this.cleanUp();
                this.router.navigate(["/campaign-home"]);
            },
            error => {
                console.log("Error caught in RegistrationComponent.loginNewUser()");
                this.errorMessage = <any>error;
                console.log("Error Message:\n" + this.errorMessage);
            });
    }

    private cleanUp() {
        this.registerSuccess = true;
        this.submitError = false;
        this.newUser = new User();
        this.passwords = new Passwords();
    }
}