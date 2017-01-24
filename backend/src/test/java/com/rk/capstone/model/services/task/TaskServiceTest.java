package com.rk.capstone.model.services.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rk.capstone.model.dao.TaskDao;
import com.rk.capstone.model.domain.Task;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

/**
 * Test Class for Task Services
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class TaskServiceTest {

    private TaskService taskService;
    private TaskDao taskDao = mock(TaskDao.class);
    private Task taskOne;
    private Task taskTwo;
    private List<Task> tasks;

    @Before
    public void setup() {
        taskService = new TaskServiceImpl(taskDao);
        taskOne = new Task(1L, new Date(), "Task One Desc", "John Doe", true);
        taskTwo = new Task(1L, new Date(), "Task Two Desc", "Jane Doe", false);
        tasks = new ArrayList<>();
        tasks.add(taskOne);
        tasks.add(taskTwo);
    }

    @Test
    public void testSaveTask() {
        given(this.taskDao.save(any(Task.class))).willReturn(taskOne);
        Task task = taskService.saveTask(taskOne);

        Assert.assertEquals(taskOne, task);
    }

    @Test
    public void testSaveNullResponse() {
        given(this.taskDao.save(any(Task.class))).willReturn(null);
        Task task = taskService.saveTask(taskOne);

        Assert.assertNull(task);
    }

    @Test
    public void testGetCampaignTasks() {
        given(this.taskDao.getTasksByCampaignId(any(Long.class))).willReturn(tasks);
        List<Task> receivedTasks = taskService.getCampaignTasks(1L);

        Assert.assertEquals(receivedTasks.size(), 2);
    }

    @Test
    public void testGetCampaignTasksNoneFound() {
        given(this.taskDao.getTasksByCampaignId(any(Long.class))).willReturn(null);
        List<Task> receivedTasks = taskService.getCampaignTasks(1L);

        Assert.assertNull(receivedTasks);
    }

    @Test
    public void testGetTaskById() {
        given(this.taskDao.findTaskByTaskId(any(Long.class))).willReturn(taskOne);
        Task task = taskService.getTaskById(1L);

        Assert.assertEquals(taskOne, task);
    }

    @Test
    public void testGetTaskByIdDoesNotExist() {
        given(this.taskDao.findTaskByTaskId(any(Long.class))).willReturn(null);
        Task task = taskService.getTaskById(1L);

        Assert.assertNull(task);
    }
}
