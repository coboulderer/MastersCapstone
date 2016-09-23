import {Injectable} from "@angular/core";
import {Http, Headers, Response} from "@angular/http";
import {Observable} from "rxjs/Observable";
import "rxjs/add/observable/throw";
import "rxjs/add/operator/catch";
import "rxjs/add/operator/map";
import {Campaign} from "../model/campaign";

@Injectable()
export class CampaignService {

    constructor(private http:Http){}

    getAllUserCampaigns() {
        console.log("CampaignService.getAllUserCampaigns()");
        let url = "http://localhost:8080/api/secure/campaign/user/" + this.getUserName();
        let header = this.getHeaders();
        return this.http.get(url, {headers: header}).
            map(this.parseData).
            catch(this.parseError);
    }

    getCampaign() {
        // TODO - Implement
    }

    createNewCampaign(campaign: Campaign) {
        console.log("CampaignService.createNewCampaign()");
        let url = "http://localhost:8080/api/secure/campaign/user/" + this.getUserName();
        let body = JSON.stringify(campaign);
        let header = this.getHeaders();
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

    private getAuthToken() {
        return sessionStorage.getItem("authToken");
    }

    private getUserName() {
        return sessionStorage.getItem("userName");
    }

    private getHeaders() {
        let header = new Headers();
        header.append("Content-Type", "application/json");
        header.append("auth-token", this.getAuthToken());
        return header;
    }
}