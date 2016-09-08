import {Component} from "@angular/core";
import {RegisterService} from "./services/register.service";

@Component({
    selector: 'app-component',
    providers: [
        RegisterService
    ],
    template:`
        <router-outlet></router-outlet>
    `
})
export class AppComponent {}