import {Injectable} from "@angular/core";
import {Http, Headers} from "@angular/http";
import {Campaign} from "../../model/campaign";
import {ResponseParseService} from "../util/response-parse.service";

@Injectable()
export class CampaignService {

    private campaignUrl:string = "http://localhost:8080/api/secure/campaign";

    constructor(private http:Http, private responseParseService: ResponseParseService){}

    getAllUserCampaigns() {
        console.log("CampaignService.getAllUserCampaigns()");
        let url = this.campaignUrl + "/user/" + this.getUserName();
        let header = this.getHeaders();
        return this.http.get(url, {headers: header}).
            map(this.responseParseService.parseData).
            catch(this.responseParseService.parseError);
    }

    getCampaign() {
        // TODO - Implement
    }

    createNewCampaign(campaign: Campaign) {
        console.log("CampaignService.createNewCampaign()");
        let url = this.campaignUrl + "/user/" + this.getUserName();
        let body = JSON.stringify(campaign);
        let header = this.getHeaders();
        return this.http.post(url, body, {headers: header}).
            map(this.responseParseService.parseData).
            catch(this.responseParseService.parseError);
    }

    updateCampaign(campaign: Campaign) {
        console.log("CampaignService.updateCampaign()");
        let url = this.campaignUrl + "/" + campaign.campaignId;
        let body = JSON.stringify(campaign);
        let header = this.getHeaders();
        return this.http.put(url, body, {headers: header}).
            map(this.responseParseService.parseData).
            catch(this.responseParseService.parseError);
    }

    deleteCampaign(campaignId: number) {
        console.log("CampaignService.deleteCampaign()");
        let url = this.campaignUrl + "/" + campaignId;
        let header = this.getHeaders();
        return this.http.delete(url, {headers: header}).
            map(this.responseParseService.parseDelete).
            catch(this.responseParseService.parseError);
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