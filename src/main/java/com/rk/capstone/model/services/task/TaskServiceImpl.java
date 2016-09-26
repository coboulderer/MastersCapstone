package com.rk.capstone.model.services.task;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rk.capstone.model.dao.TaskDao;
import com.rk.capstone.model.domain.Task;

/**
 * Implementation of Task Service
 */
@Service
public class TaskServiceImpl implements ITaskService {

    private TaskDao taskDao;

    public TaskServiceImpl(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @Override
    public Task saveTask(Task task) {
        return taskDao.save(task);
    }

    @Override
    public Task getTaskById(Long taskId) {
        return taskDao.findByTaskId(taskId);
    }

    @Override
    public List<Task> getCampaignTasks(Long campaignId) {
        return taskDao.getTasksByCampaignId(campaignId);
    }

    @Override
    public void deleteTask(Task task) {
        taskDao.delete(task);
    }
}
