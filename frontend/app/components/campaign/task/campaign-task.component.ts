import {Component, ElementRef, ViewChild, Input, OnChanges, SimpleChanges} from "@angular/core";
import {FormGroup, FormControl} from "@angular/forms";
import {CampaignTaskService} from "../../../services/data/campaign-task.service";
import {Task} from "../../../model/task";
import {Campaign} from "../../../model/campaign";

declare var jQuery: any;

@Component({
    selector: "campaign-task",
    templateUrl: "app/components/campaign/task/campaign-task.component.html"
})
export class CampaignTask implements OnChanges {

    @ViewChild("modal") modal: ElementRef;
    @Input() campaign: Campaign;

    private newTask: Task = new Task();
    private campaignTasks: Task[];

    private campaignProgress: number;

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
        console.log("CampaignTask.show() Called");
        jQuery(this.modal.nativeElement)
            .modal(data || {})
            .modal("toggle");
    }

    hide() {
        console.log("CampaignTask.hide() Called");
        this.taskForm.reset();
        jQuery(this.modal.nativeElement)
            .modal("hide");
    }

    ngOnChanges(changes: SimpleChanges): void {
        console.log("Changes Detected");
        this.loadCampaignTasks();
    }

    addTask() {
        console.log("CampaignTask.addTask() function called");
        this.newTask.campaignId = this.campaign.campaignId;
        this.campaignTaskService.createNewCampaignTask(this.newTask).subscribe(task => {
                console.log("New Task Created");
                this.campaignTasks.push(task);
                this.calculateCampaignProgress();
                this.hide();
            },
            error => {
                console.log("Error Caught in CampaignTaskComponent.addTask");
                let errorMessage = <any>error;
                console.log("Error Message:\n" + errorMessage);
            });
    }

    updateTask(task: Task) {
        console.log("CampaignTask.updateTask() called");
        this.campaignTaskService.editCampaignTask(task).subscribe(task => {
                console.log("Task Updated");
                this.loadCampaignTasks();
            },
            error => {
                console.log("Error Caught in CampaignTaskComponent.updateTask");
                let errorMessage = <any>error;
                console.log("Error Message:\n" + errorMessage);
            })
    }

    loadCampaignTasks() {
        console.log("CampaignTask.loadCampaignTasks() function called");
        this.campaignTaskService.getCampaignTasks(this.campaign.campaignId).subscribe(tasks => {
                console.log("Campaign Tasks Found");
                this.campaignTasks = tasks;
                this.calculateCampaignProgress();
            },
            error => {
                console.log("Error Caught in CampaignTaskComponent.loadCampaignTasks");
                let errorMessage = <any>error;
                console.log("Error Message:\n" + errorMessage);
            })
    }

    private calculateCampaignProgress() {
        console.log("Calculating campaign progress");
        let completed = 0;
        for (let i in this.campaignTasks) {
            if (this.campaignTasks[i].completed === true) {
                completed += 1;
            }
        }
        this.campaignTasks.length == 0 ? this.campaignProgress = 0 :
            this.campaignProgress = Math.round(100 * completed / this.campaignTasks.length);
    }
}