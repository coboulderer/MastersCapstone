package com.rk.capstone.services;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.rk.capstone.services.campaign.CampaignServiceTest;
import com.rk.capstone.services.user.UserServiceTest;

/**
 * Test Suite wrapper for all tests in com.rk.capstone.services.* package
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        CampaignServiceTest.class,
        UserServiceTest.class})
public class ServiceTests {
}
