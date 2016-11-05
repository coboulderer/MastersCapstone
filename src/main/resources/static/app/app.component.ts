import {Component} from "@angular/core";
import "./rxjs-operators";
import {ResponseParseService} from "./services/util/response-parse.service";

@Component({
    selector: 'app-component',
    template: `
        <router-outlet></router-outlet>
    `,
    providers: [
        ResponseParseService
    ],
})
export class AppComponent {
}