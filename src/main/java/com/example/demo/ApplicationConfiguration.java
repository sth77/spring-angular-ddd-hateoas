package com.example.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class ApplicationConfiguration implements WebMvcConfigurer {

	/**
	 * Redirect non-api calls to the Angular router.
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("forward:/index.html");
		registry.addViewController("/{spring:^(?!api$)[a-zA-Z\\d-_]+$}").setViewName("forward:/");
		registry.addViewController("/{spring:^(?!api$)[a-zA-Z\\d-_]+$}/{spring:^[a-zA-Z\\d-_]+$}").setViewName("forward:/");
		registry.addViewController("/{spring:^(?!api$)[a-zA-Z\\d-_]+$}/**/{spring:^[a-zA-Z\\d-_]+$}").setViewName("forward:/");
		registry.addViewController("/{spring:^(?!api$)$}/**{spring:?!(\\.js|\\.css)$}").setViewName("forward:/");
	}

}
