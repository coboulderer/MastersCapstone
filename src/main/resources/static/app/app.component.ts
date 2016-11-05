import {Component} from "@angular/core";
import "./rxjs-operators";
import {ResponseParseService} from "./services/util/response-parse.service";
import {HeaderService} from "./services/util/header.service";

@Component({
    selector: 'app-component',
    template: `
        <router-outlet></router-outlet>
    `,
    providers: [
        ResponseParseService,
        HeaderService
    ],
})
export class AppComponent {
}