import {Injectable} from "@angular/core";
import {Http, Headers, Response} from "@angular/http";
import {Observable} from "rxjs/Observable";
import {CampaignTask} from "../model/campaign-task";

@Injectable()
export class CampaignTaskService {

    constructor(private http:Http){}

    getCampaignTasks() {
        // TODO
    }

    createNewCampaignTask(task: CampaignTask) {
        console.log("Called CampaignTaskService.createNewCampaignTask");
        let url = "http://localhost:8080/api/secure/task";
        let body = JSON.stringify(task);
        let header = this.getHeaders();
        return this.http.post(url, body, {headers: header}).
            map(this.parseData).
            catch(this.parseError);
    }

    editCampaignTask() {
        // TODO
    }

    deleteCampaignTask() {
        // TODO
    }

    deleteAllCampaignTasks() {
        // TODO
    }

    private parseData(res: Response) {
        console.log("CampaignTaskService.parseData(res) called");
        let body = res.json();
        console.log("Returned JSON body" + JSON.stringify(body));
        return body || {};
    }

    private parseError(error: any) {
        console.log("CampaignTaskService.parseError(error) called");
        let errMsg = "";
        // TODO - Error specifics
        return Observable.throw(errMsg);
    }

    private getAuthToken() {
        return sessionStorage.getItem("authToken");
    }

    private getHeaders() {
        let header = new Headers();
        header.append("Content-Type", "application/json");
        header.append("auth-token", this.getAuthToken());
        return header;
    }
}