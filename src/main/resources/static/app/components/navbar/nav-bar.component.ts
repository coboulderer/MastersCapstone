import {Component} from "@angular/core";
import {LoginService} from "../../services/login.service";

@Component({
    selector: "nav-bar",
    templateUrl: "app/components/navbar/nav-bar.component.html",
    providers: [
        LoginService
    ]
})
export class NavBar {

    constructor(private loginService: LoginService){}

}
