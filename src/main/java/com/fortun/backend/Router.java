package com.fortun.backend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
public class Router {

    @Bean
    public Handler handler() {
        return new Handler();
    }

    @Bean
    public RouterFunction<ServerResponse> route(final Handler handler) {
        return RouterFunctions
                .route(GET("/hello"), handler::process);
    }

}
