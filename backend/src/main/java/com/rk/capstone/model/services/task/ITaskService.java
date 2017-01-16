package com.rk.capstone.model.services.task;

import java.util.List;

import com.rk.capstone.model.domain.Task;

/**
 * Service Interface for Tasks
 */
public interface ITaskService {

    Task saveTask(Task task);

    Task getTaskById(Long taskId);

    List<Task> getCampaignTasks(Long campaignId);

    void deleteTask(Task task);
}
