package com.themagins.filemonitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Andris Magins
 * @created 15/01/2020
 **/
@SpringBootApplication
public class DemoApplication {
	private static Logger LOG = LoggerFactory.getLogger(DemoApplication.class);

	public static void main(String[] args) {
		LOG.info("Application started");
		SpringApplication.run(DemoApplication.class, args);
	}

}
