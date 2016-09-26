package com.rk.capstone.controllers.secure.task;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<Task> createNewTask(Task task) {
        return null;
    }

    @RequestMapping(value = "/{taskId}", method = RequestMethod.PUT)
    public ResponseEntity<Task> updateTask(@PathVariable Long taskId, Task task) {
        return null;
    }

    @RequestMapping(value = "/campaign/{campaignId}", method = RequestMethod.GET)
    public ResponseEntity<List<Task>> getAllCampaignTasks(@PathVariable Long campaignId) {
        return null;
    }

    @RequestMapping(value = "/{taskId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteTask(@PathVariable Long taskId) {
        return null;
    }

    @RequestMapping(value = "/campaign/{campaignId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteAllCampaignTasks(@PathVariable Long campaignId) {
        return null;
    }
}