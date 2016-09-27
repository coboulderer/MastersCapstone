package com.rk.capstone.controllers.secure.task;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rk.capstone.model.domain.Task;
import com.rk.capstone.model.services.campaign.ICampaignService;
import com.rk.capstone.model.services.task.ITaskService;

/**
 * REST Controller for /api/secure/task endpoint
 */
@RestController
@RequestMapping(value = "/api/secure/task")
public class TaskController {

    private ITaskService taskService;
    private ICampaignService campaignService;

    public TaskController(ITaskService taskService, ICampaignService campaignService) {
        this.taskService = taskService;
        this.campaignService = campaignService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Task> createNewTask(@RequestBody Task task) {
        ResponseEntity<Task> response;
        if (task == null) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else if (campaignService.getCampaignById(task.getCampaignId()) == null) {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            task = taskService.saveTask(task);
            response = ResponseEntity.status(HttpStatus.CREATED).body(task);
        }
        return response;
    }

    @RequestMapping(value = "/{taskId}", method = RequestMethod.PUT)
    public ResponseEntity<Task> updateTask(@PathVariable Long taskId, @RequestBody Task task) {
        ResponseEntity<Task> response;
        if (taskId == null || task == null || !taskId.equals(task.getTaskId())) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else if (taskService.getTaskById(taskId) == null) {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            task = taskService.saveTask(task);
            response = ResponseEntity.status(HttpStatus.OK).body(task);
        }
        return response;
    }

    @RequestMapping(value = "/campaign/{campaignId}", method = RequestMethod.GET)
    public ResponseEntity<List<Task>> getAllCampaignTasks(@PathVariable Long campaignId) {
        ResponseEntity<List<Task>> response;
        if (campaignId == null) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else if (campaignService.getCampaignById(campaignId) == null) {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            List<Task> tasks = taskService.getCampaignTasks(campaignId);
            response = ResponseEntity.status(HttpStatus.OK).body(tasks);
        }
        return response;
    }

    @RequestMapping(value = "/{taskId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteTask(@PathVariable Long taskId) {
        ResponseEntity<String> response;
        if (taskId == null) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            Task task = taskService.getTaskById(taskId);
            if (task == null) {
                response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            } else {
                taskService.deleteTask(task);
                response = ResponseEntity.status(HttpStatus.OK).body("Task " + taskId + " deleted");
            }
        }
        return response;
    }

    @RequestMapping(value = "/campaign/{campaignId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteAllCampaignTasks(@PathVariable Long campaignId) {
        ResponseEntity<String> response;
        if (campaignId == null) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else if (campaignService.getCampaignById(campaignId) == null) {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            List<Task> tasks = taskService.getCampaignTasks(campaignId);
            tasks.forEach(task -> taskService.deleteTask(task));
            response = ResponseEntity.status(HttpStatus.OK).body("All Campaign Tasks Deleted");
        }
        return response;
    }
}
