package com.rk.capstone;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.rk.capstone.controllers.ControllerTests;
import com.rk.capstone.model.services.ServiceTests;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		ControllerTests.class,
		ServiceTests.class})
public class MastersCapstoneApplicationTests {}
