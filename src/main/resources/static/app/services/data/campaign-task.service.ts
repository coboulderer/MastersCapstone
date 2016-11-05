import {Injectable} from "@angular/core";
import {Http, Headers, Response} from "@angular/http";
import {Observable} from "rxjs/Observable";
import {Task} from "../../model/task";

@Injectable()
export class CampaignTaskService {

    private taskUrl: string = "http://localhost:8080/api/secure/task";

    constructor(private http:Http){}

    getCampaignTasks(campaignId: number) {
        console.log("Called CampaignTaskService.getCampaignTasks");
        let url = this.taskUrl + "/campaign/" + campaignId;
        let header = this.getHeaders();
        return this.http.get(url, {headers: header}).
            map(this.parseData).
            catch(this.parseError);
    }

    createNewCampaignTask(task: Task) {
        console.log("Called CampaignTaskService.createNewCampaignTask");
        let url = this.taskUrl;
        let body = JSON.stringify(task);
        let header = this.getHeaders();
        return this.http.post(url, body, {headers: header}).
            map(this.parseData).
            catch(this.parseError);
    }

    editCampaignTask(task: Task) {
        console.log("CampaignTaskService.editCampaignTask()");
        let url = this.taskUrl + "/" + task.taskId;
        let body = JSON.stringify(task);
        let header = this.getHeaders();
        return this.http.put(url, body, {headers: header}).
            map(this.parseData).
            catch(this.parseError);
    }

    deleteCampaignTask(taskId: number) {
        console.log("CampaignTaskService.deleteCampaignTask");
        let url = this.taskUrl + "/" + taskId;
        let header = this.getHeaders();
        return this.http.delete(url, {headers: header}).
            map(this.parseDelete).
            catch(this.parseError);
    }

    deleteAllCampaignTasks(campaignId: number) {
        console.log("CampaignTaskService.deleteAllCampaignTasks()");
        let url = this.taskUrl + "/campaign/" + campaignId;
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
        if (error.status == 400) {
            errMsg = "A Bad Request was made - try your action again";
        } else if (error.status == 404) {
            errMsg = "The resource(s) you requested could not be found on the server";
        } else {
            errMsg = "An unknown error occurred processing your request";
        }
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