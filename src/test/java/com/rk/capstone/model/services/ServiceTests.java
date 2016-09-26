package com.rk.capstone.model.services;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.rk.capstone.model.services.campaign.CampaignServiceTest;
import com.rk.capstone.model.services.task.TaskServiceTest;
import com.rk.capstone.model.services.user.UserServiceTest;

/**
 * Test Suite wrapper for all tests in com.rk.capstone.model.services.* package
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        CampaignServiceTest.class,
        UserServiceTest.class,
        TaskServiceTest.class})
public class ServiceTests {
}
