package com.rk.capstone.controllers.secure.task;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.rk.capstone.model.services.campaign.ICampaignService;
import com.rk.capstone.model.services.task.ITaskService;

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

    @Before
    public void setup() {

    }
}
