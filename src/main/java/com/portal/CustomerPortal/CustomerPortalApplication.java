package com.portal.CustomerPortal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.portal")
@EnableAspectJAutoProxy
@EnableJpaRepositories(basePackages = "com.portal.dao")
@EntityScan(basePackages = "com.portal.entity")
public class CustomerPortalApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(CustomerPortalApplication.class, args);

	}
}
