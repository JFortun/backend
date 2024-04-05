package com.fortun.backend;

import java.time.LocalDateTime;

/**
 * Record containing the data needed for the response of the prices endpoint
 *
 * @param productId
 * @param brandId
 * @param priceList
 * @param startDate
 * @param endDate
 * @param price
 */
record PriceResponse(
        int productId,
        int brandId,
        int priceList,
        LocalDateTime startDate,
        LocalDateTime endDate,
        double price) {
}
