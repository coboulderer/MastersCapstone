package com.rk.capstone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.rk.capstone.config.filter.AuthTokenFilter;

@SpringBootApplication
public class MastersCapstoneApplication {

	@Bean
	public FilterRegistrationBean authTokenFilter(){
		final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(new AuthTokenFilter());
		registrationBean.addUrlPatterns("/api/secure/*");
		return registrationBean;
	}

	public static void main(String[] args) {
		SpringApplication.run(MastersCapstoneApplication.class, args);
	}
}
