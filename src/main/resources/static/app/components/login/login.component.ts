import {Component} from "@angular/core";
import {LoginService} from "../../services/login.service";

@Component({
    selector: "login",
    templateUrl: "app/components/login/login.component.html",
    providers: [
        LoginService
    ]
})
export class Login {

    private credentials = {"username": "", "password": ""};

    private loginError: boolean = false;
    private errorMessage: string;

    constructor(private loginService: LoginService){}

    onSubmit() {
        console.log("Login.onSubmit() function called");
        this.loginService.sendCredentials(this.credentials).subscribe(response => {
                console.log("Login Successful - Parsing Response");
                console.log("FULL RESPONSE: " + JSON.stringify(response));
                let authToken = JSON.parse(JSON.stringify(response))._body;
                console.log("AuthToken:: " + authToken);
                // TODO - Finish processing, redirect to campaign home
            },
            error => {
                console.log("Error caught in RegistrationComponent.onSubmit()");
                this.errorMessage = <any>error;
                this.loginError = true;
                console.log("Error Message:\n" + this.errorMessage);
            });
    }
}