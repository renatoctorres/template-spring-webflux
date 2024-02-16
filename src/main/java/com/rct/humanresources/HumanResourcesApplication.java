package com.rct.humanresources;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import static org.springframework.boot.SpringApplication.run;

/**
 * Spring WebFlux Kotlin Template - Human Resources Application
 */
@EnableMongoAuditing
@EnableReactiveMongoRepositories
@OpenAPIDefinition(info = @Info(title = "APIs", version = "1.0", description = "Documentation APIs v1.0"))
@SpringBootApplication
public class HumanResourcesApplication {
//	static {
//		BlockHound.install();
//	}
	/**
	 * Main Spring WebFlux Template - Run Method
	 * @param args Arguments Run
	 */
	public static void main(String[] args) {
		run(HumanResourcesApplication.class, args);
	}

}
