import {Component} from "@angular/core";
import {Router} from "@angular/router";
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

    constructor(private loginService: LoginService, private router: Router){}

    onSubmit() {
        console.log("Login.onSubmit() function called");
        this.loginService.login(this.credentials).subscribe(response => {
                console.log("Login Successful");
                this.loginService.parseLoginResponse(response, this.credentials);
                this.router.navigate(["/campaign-home"]);
            },
            error => {
                console.log("Error caught in LoginComponent.onSubmit()");
                this.errorMessage = <any>error;
                this.loginError = true;
                console.log("Error Message:\n" + this.errorMessage);
            });
    }
}