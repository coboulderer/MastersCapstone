import {Injectable} from "@angular/core";
import {Http, Headers, Response} from "@angular/http";
import {Observable} from "rxjs/Observable";
import "rxjs/add/observable/throw";
import "rxjs/add/operator/catch";
import "rxjs/add/operator/map";
import {Campaign} from "../model/campaign";

@Injectable()
export class CampaignService {

    private authToken: string;
    private userName: string;

    constructor(private http:Http){
        this.authToken = sessionStorage.getItem("authToken");
        this.userName = sessionStorage.getItem("userName");
    }

    getAllUserCampaigns() {
        // TODO - Implement
    }

    getCampaign() {
        // TODO - Implement
    }

    createNewCampaign(campaign: Campaign): Observable<Campaign> {
        console.log("CampaignService.createNewCampaign()");
        let url = "http://localhost:8080/api/secure/campaign/user/" + this.userName;
        let body = JSON.stringify(campaign);
        let header = new Headers();
        header.append("Content-Type", "application/json");
        header.append("auth-token", this.authToken);
        return this.http.post(url, body, {headers: header}).
            map(this.parseData).
            catch(this.parseError);
    }

    updateCampaign() {
        // TODO - Implement
    }

    deleteCampaign() {
        // TODO - Implement
    }

    private parseData(res: Response) {
        console.log("CampaignService.parseData(res) called");
        let body = res.json();
        console.log("Returned JSON body" + JSON.stringify(body));
        return body || {};
    }

    private parseError(error: any) {
        console.log("RegisterService.parseError(error) called");
        let errMsg = "Caught error " + error.status;
        // TODO ERROR SPECIFICS
        return Observable.throw(errMsg);
    }
}