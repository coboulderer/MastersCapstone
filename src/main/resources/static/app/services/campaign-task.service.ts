import {Injectable} from "@angular/core";
import {Http, Headers, Response} from "@angular/http";
import {Observable} from "rxjs/Observable";
import {Task} from "../model/task";

@Injectable()
export class CampaignTaskService {

    constructor(private http:Http){}

    getCampaignTasks(campaignId: number) {
        console.log("Called CampaignTaskService.getCampaignTasks");
        let url = "http://localhost:8080/api/secure/task/campaign/" + campaignId;
        let header = this.getHeaders();
        return this.http.get(url, {headers: header}).
            map(this.parseData).
            catch(this.parseError);
    }

    createNewCampaignTask(task: Task) {
        console.log("Called CampaignTaskService.createNewCampaignTask");
        let url = "http://localhost:8080/api/secure/task";
        let body = JSON.stringify(task);
        let header = this.getHeaders();
        return this.http.post(url, body, {headers: header}).
            map(this.parseData).
            catch(this.parseError);
    }

    editCampaignTask(task: Task) {
        console.log("CampaignTaskService.editCampaignTask()");
        let url = "http://localhost:8080/api/secure/task/" + task.taskId;
        let body = JSON.stringify(task);
        let header = this.getHeaders();
        return this.http.put(url, body, {headers: header}).
            map(this.parseData).
            catch(this.parseError);
    }

    deleteCampaignTask() {
        // TODO
    }

    deleteAllCampaignTasks(campaignId: number) {
        console.log("CampaignTaskService.deleteAllCampaignTasks()");
        let url = "http://localhost:8080/api/secure/task/campaign/" + campaignId;
        let header = this.getHeaders();
        return this.http.delete(url, {headers: header}).
        map(this.parseDelete).
        catch(this.parseError);
    }

    private parseData(res: Response) {
        console.log("CampaignTaskService.parseData(res) called");
        let body = res.json();
        console.log("Returned JSON body" + JSON.stringify(body));
        return body || {};
    }

    private parseDelete(response: Response) {
        console.log("CampaignTaskService.parseDelete(response) called");
        let resString = JSON.parse(JSON.stringify(response))._body;
        return resString || {};
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