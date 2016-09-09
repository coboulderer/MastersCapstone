import {Component} from "@angular/core";
import "./rxjs-operators";

@Component({
    selector: 'app-component',
    template: `
        <router-outlet></router-outlet>
    `
})
export class AppComponent {
}