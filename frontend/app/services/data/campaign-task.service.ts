import {Injectable} from "@angular/core";
import {Http} from "@angular/http";
import {Task} from "../../model/task";
import {ResponseParseService} from "../util/response-parse.service";
import {HeaderService} from "../util/header.service";

@Injectable()
export class CampaignTaskService {

    private taskUrl: string = "http://localhost:8080/api/secure/task";

    constructor(private http:Http, private responseParseService: ResponseParseService,
                private headerService: HeaderService){}

    getCampaignTasks(campaignId: number) {
        console.log("Called CampaignTaskService.getCampaignTasks");
        let url = this.taskUrl + "/campaign/" + campaignId;
        let header = this.headerService.getStandardHeaders();
        return this.http.get(url, {headers: header}).
            map(this.responseParseService.parseData).
            catch(this.responseParseService.parseError);
    }

    createNewCampaignTask(task: Task) {
        console.log("Called CampaignTaskService.createNewCampaignTask");
        let url = this.taskUrl;
        let body = JSON.stringify(task);
        let header = this.headerService.getStandardHeaders();
        return this.http.post(url, body, {headers: header}).
            map(this.responseParseService.parseData).
            catch(this.responseParseService.parseError);
    }

    editCampaignTask(task: Task) {
        console.log("CampaignTaskService.editCampaignTask()");
        let url = this.taskUrl + "/" + task.taskId;
        let body = JSON.stringify(task);
        let header = this.headerService.getStandardHeaders();
        return this.http.put(url, body, {headers: header}).
            map(this.responseParseService.parseData).
            catch(this.responseParseService.parseError);
    }

    deleteCampaignTask(taskId: number) {
        console.log("CampaignTaskService.deleteCampaignTask");
        let url = this.taskUrl + "/" + taskId;
        let header = this.headerService.getStandardHeaders();
        return this.http.delete(url, {headers: header}).
            map(this.responseParseService.parseDelete).
            catch(this.responseParseService.parseError);
    }

    deleteAllCampaignTasks(campaignId: number) {
        console.log("CampaignTaskService.deleteAllCampaignTasks()");
        let url = this.taskUrl + "/campaign/" + campaignId;
        let header = this.headerService.getStandardHeaders();
        return this.http.delete(url, {headers: header}).
            map(this.responseParseService.parseDelete).
            catch(this.responseParseService.parseError);
    }
}