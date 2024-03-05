package com.rct.humanresources;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import reactor.blockhound.BlockHound;

import static org.springframework.boot.SpringApplication.run;

/**
 * Spring WebFlux Kotlin Template - Human Resources Application
 */
@EnableReactiveMongoRepositories
@OpenAPIDefinition(info = @Info(title = "APIs", version = "1.0", description = "Documentation APIs v1.0"))
@SpringBootApplication
public class HumanResourcesApplication {

	static {
		BlockHound.builder()
				.allowBlockingCallsInside(
						"com.rct.humanresources.infra.config.MongoDBDataInitializerConfig", "run")
				.install();
	}
	/**
	 * Main Spring WebFlux Template - Run Method
	 * @param args Arguments Run
	 */
	public static void main(String[] args) {
		run(HumanResourcesApplication.class, args);
	}

}
