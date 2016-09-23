package com.rk.capstone.controllers;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.rk.capstone.controllers.login.LoginControllerTest;
import com.rk.capstone.controllers.register.RegisterControllerTest;
import com.rk.capstone.controllers.secure.campaign.CampaignControllerTest;

/**
 * Test Suite Containing all REST Controller Tests in com.rk.capstone.controllers.*
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        CampaignControllerTest.class,
        LoginControllerTest.class,
        RegisterControllerTest.class})
public class ControllerTests {
}