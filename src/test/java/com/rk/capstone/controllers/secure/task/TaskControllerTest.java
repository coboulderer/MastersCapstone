package com.rk.capstone.controllers.secure.task;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rk.capstone.model.domain.Campaign;
import com.rk.capstone.model.domain.Task;
import com.rk.capstone.model.domain.User;
import com.rk.capstone.model.services.campaign.ICampaignService;
import com.rk.capstone.model.services.task.ITaskService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Class Provides Unit Testing for TaskController
 */
@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @MockBean
    private ICampaignService campaignService;
    @MockBean
    private ITaskService taskService;

    @Autowired
    private MockMvc mockMvc;

    private Task taskOne;
    private Task taskTwo;
    private Campaign campaign;
    private User user;
    private String authToken;
    private String userName;
    private ObjectMapper objectMapper;
    private ArrayList<Task> tasks;

    @Before
    public void setup() {
        userName = "JohnDoe";
        authToken = Jwts.builder().setSubject(userName).claim("roles", "user").
                setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256, "secretkey").
                compact();

        taskOne = new Task(1L, new Date(), "A Description", "Jane Doe", false);
        taskTwo = new Task(1L, new Date(), "Another Description", "Joe Dirt", true);
        tasks = new ArrayList<>();
        tasks.add(taskOne);
        tasks.add(taskTwo);
        user = new User("John", "Doe", "johnd@email", userName, "abc123", null);
        campaign = new Campaign(1L, "Campaign", "commit", "Summary", "in progress", new Date(), new Date(), 1, user);
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testPostNewTask() throws Exception {
        given(this.campaignService.getCampaignById(any(Long.class))).willReturn(campaign);
        given(this.taskService.saveTask(any(Task.class))).willReturn(taskOne);
        String taskJson = objectMapper.writeValueAsString(taskOne);

        MvcResult result = this.mockMvc.perform(post("/api/secure/task").
                header("auth-token", authToken).
                contentType(MediaType.APPLICATION_JSON).
                content(taskJson).
                accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isCreated()).
                andDo(print()).
                andReturn();
    }

    @Test
    public void testPostNewTaskWithBadCampaignId() throws Exception {
        given(this.campaignService.getCampaignById(any(Long.class))).willReturn(null);
        String taskJson = objectMapper.writeValueAsString(taskOne);

        MvcResult result = this.mockMvc.perform(post("/api/secure/task").
                header("auth-token", authToken).
                contentType(MediaType.APPLICATION_JSON).
                content(taskJson).
                accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isNotFound()).
                andDo(print()).
                andReturn();
    }

    @Test
    public void testPutUpdateTask() throws Exception {
        Long taskId = 1L;
        taskOne.setTaskId(taskId);
        String taskJson = objectMapper.writeValueAsString(taskOne);
        given(this.taskService.getTaskById(any(Long.class))).willReturn(taskOne);
        given(this.taskService.saveTask(any(Task.class))).willReturn(taskOne);

        MvcResult result = this.mockMvc.perform(put("/api/secure/task/" + taskId).
                header("auth-token", authToken).
                contentType(MediaType.APPLICATION_JSON).
                content(taskJson).
                accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).
                andDo(print()).
                andReturn();
    }

    @Test
    public void testPutUpdateTaskUnequalIds() throws Exception {
        Long taskId = 1L;
        taskOne.setTaskId(2L);
        String taskJson = objectMapper.writeValueAsString(taskOne);

        MvcResult result = this.mockMvc.perform(put("/api/secure/task/" + taskId).
                header("auth-token", authToken).
                contentType(MediaType.APPLICATION_JSON).
                content(taskJson).
                accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isBadRequest()).
                andDo(print()).
                andReturn();
    }

    @Test
    public void testPutUpdateTaskDoesNotExist() throws Exception {
        Long taskId = 1L;
        taskOne.setTaskId(taskId);
        given(this.taskService.getTaskById(any(Long.class))).willReturn(null);
        String taskJson = objectMapper.writeValueAsString(taskOne);

        MvcResult result = this.mockMvc.perform(put("/api/secure/task/" + taskId).
                header("auth-token", authToken).
                contentType(MediaType.APPLICATION_JSON).
                content(taskJson).
                accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isNotFound()).
                andDo(print()).
                andReturn();
    }

    @Test
    public void testGetTasksByCampaignId() throws Exception {
        Long campaignId = 1L;
        campaign.setCampaignId(campaignId);
        given(this.campaignService.getCampaignById(any(Long.class))).willReturn(campaign);
        given(this.taskService.getCampaignTasks(any(Long.class))).willReturn(tasks);

        MvcResult result = this.mockMvc.perform(get("/api/secure/task/campaign/" + campaignId).
                header("auth-token", authToken).
                accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).
                andDo(print()).
                andReturn();
    }

    @Test
    public void testGetTasksByCampaignIdCampaignDoesNotExist() throws Exception {
        Long campaignId = 1L;
        given(this.campaignService.getCampaignById(any(Long.class))).willReturn(null);

        MvcResult result = this.mockMvc.perform(get("/api/secure/task/campaign/" + campaignId).
                header("auth-token", authToken).
                accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isNotFound()).
                andDo(print()).
                andReturn();
    }

    @Test
    public void testDeleteTask() throws Exception {
        given(this.taskService.getTaskById(any(Long.class))).willReturn(taskOne);

        MvcResult result = this.mockMvc.perform(delete("/api/secure/task/" + 1).
                header("auth-token", authToken)).
                andExpect(status().isOk()).
                andDo(print()).
                andReturn();
    }

    @Test
    public void testDeleteTaskDoesNotExist() throws Exception {
        given(this.taskService.getTaskById(any(Long.class))).willReturn(null);

        MvcResult result = this.mockMvc.perform(delete("/api/secure/task/" + 1).
                header("auth-token", authToken)).
                andExpect(status().isNotFound()).
                andDo(print()).
                andReturn();
    }

    @Test
    public void testDeleteAllCampaignTasks() throws Exception {
        given(this.campaignService.getCampaignById(any(Long.class))).willReturn(campaign);
        given(this.taskService.getCampaignTasks(any(Long.class))).willReturn(tasks);

        MvcResult result = this.mockMvc.perform(delete("/api/secure/task/campaign/" + 1).
                header("auth-token", authToken)).
                andExpect(status().isOk()).
                andDo(print()).
                andReturn();
    }

    @Test
    public void testDeleteAllCampaignTasksCampaignNotFound() throws Exception {
        given(this.campaignService.getCampaignById(any(Long.class))).willReturn(null);

        MvcResult result = this.mockMvc.perform(delete("/api/secure/task/campaign/" + 1).
                header("auth-token", authToken)).
                andExpect(status().isNotFound()).
                andDo(print()).
                andReturn();
    }
}
