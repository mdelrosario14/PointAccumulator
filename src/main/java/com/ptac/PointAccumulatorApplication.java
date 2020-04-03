package com.ptac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This class serves as the Spring Boot's entry point.
 * @author Mardolfh Del Rosario
 *
 */
@SpringBootApplication
public class PointAccumulatorApplication {

	/**
	 * Main class that will run the Spring boot app.
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		SpringApplication.run(PointAccumulatorApplication.class, args);
	}
}
