package com.fortun.backend.price.application;

import com.fortun.backend.prices.domain.PriceResponse;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface PriceRepository extends ReactiveCrudRepository<PriceResponse, Integer> {

    /**
     * Method to query the price based on a criteria
     *
     * @param requestDateTime the time at which the request was made
     * @param brandId         id of the brand
     * @param productId       id of the product
     * @return a reactive response of the price that matches the query criteria
     */
    @Query("""
            SELECT p.START_DATE, p.END_DATE, p.BRAND_ID, p.PRODUCT_ID, p.PRICE_LIST, p.PRICE
            FROM PRICES p
            WHERE p.BRAND_ID = :brandId
            AND p.PRODUCT_ID = :productId
            AND :requestDateTime BETWEEN p.START_DATE AND p.END_DATE
            ORDER BY p.PRIORITY DESC
            LIMIT 1
            """)
    Mono<PriceResponse> findPrice(LocalDateTime requestDateTime, int brandId, int productId);
}
