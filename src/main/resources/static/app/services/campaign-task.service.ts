import {Injectable} from "@angular/core";
import {Http, Headers} from "@angular/http";

@Injectable()
export class CampaignTaskService {

    constructor(private http:Http){}

    getCampaignTasks() {
        // TODO
    }

    createNewCampaignTask() {
        // TODO
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