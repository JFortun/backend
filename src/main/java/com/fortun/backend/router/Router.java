package com.fortun.backend.router;

import com.fortun.backend.handler.Handler;
import com.fortun.backend.repository.PriceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

/**
 * Class containing the configuration of the beans for routing the server requests to the correct handler
 */
@Configuration
class Router {

    @Bean
    Handler handler(final PriceRepository priceRepository) {
        return new Handler(priceRepository);
    }

    @Bean
    RouterFunction<ServerResponse> route(final Handler handler) {
        return RouterFunctions
                .route(GET("/price"), handler::process);
    }

}
