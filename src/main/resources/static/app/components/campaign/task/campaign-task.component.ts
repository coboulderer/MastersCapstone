import {Component, ElementRef, ViewChild, Input} from "@angular/core";
import {FormGroup, FormControl} from "@angular/forms";
import {CampaignTaskService} from "../../../services/campaign-task.service";
import {Task} from "../../../model/task";
import {Campaign} from "../../../model/campaign";

declare var jQuery: any;

@Component({
    selector: "campaign-task",
    templateUrl: "app/components/campaign/task/campaign-task.component.html"
})
export class CampaignTask {

    @ViewChild("modal") modal: ElementRef;
    @Input() campaign: Campaign;

    private newTask: Task = new Task();
    private campaignTasks: Task[];

    private taskForm = new FormGroup({
        description     : new FormControl(),
        assignee        : new FormControl(),
        dueDate         : new FormControl(),
        completed       : new FormControl()
    });

    constructor(private campaignTaskService: CampaignTaskService){
        this.campaignTasks = [];
    }

    show(data?: {}) {
        console.log("CampaignNew.show() Called");
        jQuery(this.modal.nativeElement)
            .modal(data || {})
            .modal("toggle");
    }

    hide() {
        console.log("CampaignNew.hide() Called");
        this.taskForm.reset();
        jQuery(this.modal.nativeElement)
            .modal("hide");
    }

    addTask() {
        console.log("CampaignTask.addTask() function called");
        this.newTask.campaignId = this.campaign.campaignId;
        this.campaignTaskService.createNewCampaignTask(this.newTask).subscribe(task => {
                console.log("New Task Created");
                this.campaignTasks.push(task);
                this.hide();
            },
            error => {
                console.log("Error Caught in CampaignTaskService.createNewCampaignTask");
                let errorMessage = <any>error;
                console.log("Error Message:\n" + errorMessage);
            });
    }
}