package com.rk.capstone.controllers.secure.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rk.capstone.model.domain.Task;
import com.rk.capstone.model.services.campaign.CampaignService;
import com.rk.capstone.model.services.task.TaskService;

/**
 * REST Controller for /api/secure/task endpoint
 */
@RestController
@RequestMapping(value = "/api/secure/task")
public class TaskController {

    private TaskService taskService;
    private CampaignService campaignService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public TaskController(TaskService taskService, CampaignService campaignService) {
        this.taskService = taskService;
        this.campaignService = campaignService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Task> createNewTask(@RequestBody Task task) {
        ResponseEntity<Task> response;
        logger.info("Attempting to create a new task");
        if (task == null) {
            logger.error("Cannot create a null task, create failed");
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else if (campaignService.getCampaignById(task.getCampaignId()) == null) {
            logger.error("Could not find the campaign this task belongs to, create failed");
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            logger.info("Creating task - " + task.toString());
            task = taskService.saveTask(task);
            response = ResponseEntity.status(HttpStatus.CREATED).body(task);
        }
        return response;
    }

    @RequestMapping(value = "/{taskId}", method = RequestMethod.PUT)
    public ResponseEntity<Task> updateTask(@PathVariable Long taskId, @RequestBody Task task) {
        ResponseEntity<Task> response;
        logger.info("Attempting to update task with id = " + taskId);
        if (task == null || !taskId.equals(task.getTaskId())) {
            logger.error("Unable to update a null task or a task without an Id - update failed");
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else if (taskService.getTaskById(taskId) == null) {
            logger.error("Cannot update a non-existent task, taskId " + taskId + " was not found");
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            logger.info("Updating task with id = " + taskId);
            task = taskService.saveTask(task);
            response = ResponseEntity.status(HttpStatus.OK).body(task);
        }
        return response;
    }

    @RequestMapping(value = "/campaign/{campaignId}", method = RequestMethod.GET)
    public ResponseEntity<List<Task>> getAllCampaignTasks(@PathVariable Long campaignId) {
        ResponseEntity<List<Task>> response;
        logger.info("Attempting to retrieve all campaign tasks");
        if (campaignService.getCampaignById(campaignId) == null) {
            logger.error("Could not find the campaign whose tasks are wanted.  Campaign id = " +
                    campaignId + " not found");
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            logger.info("Found Campaign and tasks");
            List<Task> tasks = taskService.getCampaignTasks(campaignId);
            response = ResponseEntity.status(HttpStatus.OK).body(tasks);
            logger.info("Returning " + tasks.size() + " tasks");
        }
        return response;
    }

    @RequestMapping(value = "/{taskId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteTask(@PathVariable Long taskId) {
        ResponseEntity<String> response;
        logger.info("Attempting to delete taskId = " + taskId);

        Task task = taskService.getTaskById(taskId);
        if (task == null) {
            logger.error("The taskId " + taskId + " was not found, delete failed");
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            logger.info("Task found, deleting task with id = " + taskId);
            taskService.deleteTask(task);
            response = ResponseEntity.status(HttpStatus.OK).body("Task " + taskId + " deleted");
        }
        return response;
    }

    @RequestMapping(value = "/campaign/{campaignId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteAllCampaignTasks(@PathVariable Long campaignId) {
        ResponseEntity<String> response;
        logger.info("Attempting to delete all tasks for a campaignId = " + campaignId);
        if (campaignService.getCampaignById(campaignId) == null) {
            logger.error("The campaign with id = " + campaignId + " was not found, cannot delete " +
                    "tasks");
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            logger.info("Campaign with id = " + campaignId + " found, deleting all tasks");
            List<Task> tasks = taskService.getCampaignTasks(campaignId);
            logger.info("Deleting " + tasks.size() + " tasks for campaignId " + campaignId);
            tasks.forEach(task -> taskService.deleteTask(task));
            response = ResponseEntity.status(HttpStatus.OK).body("All Campaign Tasks Deleted");
        }
        return response;
    }
}
