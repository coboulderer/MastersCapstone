package com.rk.capstone.controllers;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.rk.capstone.controllers.login.LoginControllerTest;
import com.rk.capstone.controllers.register.RegisterControllerTest;
import com.rk.capstone.controllers.secure.campaign.CampaignControllerTest;
import com.rk.capstone.controllers.secure.customer.company.CustomerCompanyControllerTest;
import com.rk.capstone.controllers.secure.task.TaskControllerTest;

/**
 * Test Suite Containing all REST Controller Tests in com.rk.capstone.controllers.*
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        CampaignControllerTest.class,
        CustomerCompanyControllerTest.class,
        LoginControllerTest.class,
        RegisterControllerTest.class,
        TaskControllerTest.class})
public class ControllerTests {
}
