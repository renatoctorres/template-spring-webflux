package com.rct.humanresources.infra.client;

import com.rct.humanresources.infra.persistence.model.Employee;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import static java.time.Duration.ofSeconds;
import static org.springframework.http.MediaType.APPLICATION_JSON;

/**
 * WebClientUtil to Call Rest API
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class WebClientUtil {
    private final WebClient webClient;
    /**
     * Get Fake Users
     * @return ResponseSpec
     */
    public WebClient.ResponseSpec getFakeUsers() {
        return webClient
                .get()
                .uri("https://randomuser.me/api/")
                .retrieve();
    }

    /**
     * Post Employee
     * @param employee Employee
     * @return Mono User
     */
    public Mono<Employee> postEmployee(Employee employee) {
        return webClient
                .post()
                .uri("http://localhost:8080/employees")
                .header("Authorization", "Basic MY_PASSWORD")
                .accept(APPLICATION_JSON)
                .body(Mono.just(employee), Employee.class)
                .retrieve()
                .bodyToMono(Employee.class)
                .log()
                .retryWhen(Retry.backoff(10, ofSeconds(2)))
                .onErrorReturn(new Employee())
                .doOnError(throwable -> log.error("Result error out for POST employee", throwable))
                .doFinally(signalType -> log.info("Result Completed for POST Employee: {}", signalType));
    }
}