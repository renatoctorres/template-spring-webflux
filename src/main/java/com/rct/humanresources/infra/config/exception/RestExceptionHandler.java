package com.rct.humanresources.infra.config.exception;

import org.jetbrains.annotations.NotNull;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static reactor.core.publisher.Mono.error;


@Component
@Order(-2)
public class RestExceptionHandler implements WebExceptionHandler {
    @Override
    public @NotNull Mono<Void> handle(@NotNull ServerWebExchange exchange, @NotNull Throwable ex) {
        if (ex instanceof ResourceNotFoundException) {
            exchange.getResponse().setStatusCode(NOT_FOUND);
            return exchange.getResponse().setComplete();
        }
        if(ex instanceof ResourceBadRequestException){
            exchange.getResponse().setStatusCode(BAD_REQUEST);
            return exchange.getResponse().setComplete();
        }

        return error(ex);
    }
}
