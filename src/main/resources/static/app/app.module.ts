import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {FormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";
import {AppComponent} from "./app.component";
import {routing} from "./app.routing";
import {Login} from "./components/login/login.component";
import {Registration} from "./components/registration/registration.component";

@NgModule({
    imports:[
        BrowserModule,
        FormsModule,
        HttpModule,
        routing
    ],
    declarations:[
        AppComponent,
        Login,
        Registration
    ],
    bootstrap: [
        AppComponent
    ]
})
export class AppModule{}