import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";
import {AppComponent} from "./app.component";
import {routing} from "./app.routing";
import {Login} from "./components/login/login.component";
import {Registration} from "./components/registration/registration.component";
import {CampaignHome} from "./components/campaign/home/campaign-home.component";
import {CustomerHome} from "./components/customer/home/customer-home.component";
import {NavBar} from "./components/navbar/nav-bar.component";
import {NgSemanticModule} from "ng-semantic";
import {CampaignNew} from "./components/campaign/new/campaign-new.component";

@NgModule({
    imports:[
        BrowserModule,
        FormsModule,
        ReactiveFormsModule,
        HttpModule,
        routing,
        NgSemanticModule
    ],
    declarations:[
        AppComponent,
        Login,
        Registration,
        CampaignHome,
        CustomerHome,
        CampaignNew,
        NavBar
    ],
    bootstrap: [
        AppComponent
    ]
})
export class AppModule{}