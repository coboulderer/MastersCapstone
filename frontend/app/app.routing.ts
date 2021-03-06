import {ModuleWithProviders} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {Login} from "./components/login/login.component";
import {Registration} from "./components/registration/registration.component";
import {CampaignHome} from "./components/campaign/home/campaign-home.component";
import {CustomerHome} from "./components/customer/home/customer-home.component";

const appRoutes: Routes = [

    {
        path: "index",
        redirectTo: "login",
        pathMatch: "full"
    },
    {
        path: "",
        redirectTo: "login",
        pathMatch: "full"
    },
    {
        path: "login",
        component: Login
    },
    {
        path: "registration",
        component: Registration
    },
    {
        path: "campaign-home",
        component: CampaignHome
    },
    {
        path: "customer-home",
        component: CustomerHome
    }
];

export const routing: ModuleWithProviders = RouterModule.forRoot(appRoutes);