package com.fortun.backend.price.infrastructure;

import com.fortun.backend.price.application.PriceHandler;
import com.fortun.backend.price.application.PriceRepository;
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
class PriceRouter {

    @Bean
    PriceHandler handler(final PriceRepository priceRepository) {
        return new PriceHandler(priceRepository);
    }

    @Bean
    RouterFunction<ServerResponse> route(final PriceHandler priceHandler) {
        return RouterFunctions
                .route(GET("/price"), priceHandler::process);
    }

}
