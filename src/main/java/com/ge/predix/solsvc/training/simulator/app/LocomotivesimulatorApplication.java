package com.ge.predix.solsvc.training.simulator.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAutoConfiguration
@ComponentScan(basePackages={"com.ge.predix.solsvc.training.simulator.*"})
public class LocomotivesimulatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocomotivesimulatorApplication.class, args);
	}
}
