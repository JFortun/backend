package com.fortun.backend.handler;

import com.fortun.backend.model.PriceResponse;
import com.fortun.backend.repository.PriceRepository;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Class containing the handlers for the web server router
 */
public class Handler {

    private final PriceRepository priceRepository;

    /**
     * Constructor for the handler bean
     *
     * @param priceRepository the repository of the data
     */
    public Handler(final PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    /**
     * Handler to find the price
     *
     * @param request the request sent to the server
     * @return the reactive response of the server
     */
    public Mono<ServerResponse> process(final ServerRequest request) {
        final var requestDateTime = LocalDateTime.parse(request.queryParam("requestDateTime").orElseThrow(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        final var productId       = Integer.parseInt(request.queryParam("productId").orElseThrow());
        final var brandId         = Integer.parseInt(request.queryParam("brandId").orElseThrow());
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(this.priceRepository.findPrice(requestDateTime, brandId, productId), PriceResponse.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

}
