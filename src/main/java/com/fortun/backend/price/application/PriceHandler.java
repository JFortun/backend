package com.fortun.backend.price.application;

import com.fortun.backend.price.domain.PriceResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Class containing the handlers for the web server router
 */
public class PriceHandler {

    private final PriceRepository priceRepository;

    /**
     * Constructor for the handler bean
     *
     * @param priceRepository the repository of the data
     */
    public PriceHandler(final PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    /**
     * Handler to find the price
     *
     * @param request the request sent to the server
     * @return the reactive response of the server
     */
    public Mono<ServerResponse> process(final ServerRequest request) {
        final var requestDateTimeRaw = request.queryParam("requestDateTime").orElseThrow();
        final var productIdRaw       = request.queryParam("productId").orElseThrow();
        final var brandIdRaw         = request.queryParam("brandId").orElseThrow();

        // Check for correct request query params
        if (requestDateTimeRaw.isBlank() || productIdRaw.isBlank() || brandIdRaw.isBlank()) {
            return ServerResponse.badRequest().bodyValue("All of the query params must be provided");
        }

        try {
            final var requestDateTime = LocalDateTime.parse(requestDateTimeRaw, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            final var productId       = Integer.parseInt(productIdRaw);
            final var brandId         = Integer.parseInt(brandIdRaw);

            return ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(this.priceRepository.findPrice(requestDateTime, brandId, productId), PriceResponse.class)
                    .switchIfEmpty(ServerResponse.notFound().build());
        } catch (final Throwable throwable) {
            return ServerResponse.status(HttpStatusCode.valueOf(500)).bodyValue(throwable.getMessage());
        }
    }

}
