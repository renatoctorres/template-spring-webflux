package com.rct.humanresources.infra.client;

import com.rct.humanresources.infra.persistence.model.Employer;
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
public class WebClientUtil {

    private final WebClient webClient;

    /**
     * WebClient Constructor
     * @param webClient WebClient
     */
    @Autowired
    public WebClientUtil(WebClient webClient) {
        this.webClient = webClient;
    }

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
     * Post Employer
     * @param employer Employer
     * @return Mono User
     */
    public Mono<Employer> postEmployer(Employer employer) {
        return webClient
                .post()
                .uri("http://localhost:9000/api/employers")
                .header("Authorization", "Basic MY_PASSWORD")
                .accept(APPLICATION_JSON)
                .body(Mono.just(employer), Employer.class)
                .retrieve()
                .bodyToMono(Employer.class)
                .log()
                .retryWhen(Retry.backoff(10, ofSeconds(2)))
                .onErrorReturn(new Employer())
                .doOnError(throwable -> log.error("Result error out for POST employer", throwable))
                .doFinally(signalType -> log.info("Result Completed for POST Employer: {}", signalType));
    }
}