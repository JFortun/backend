package com.fortun.backend;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class BackendApplicationTests {

    private static final String URL_TEMPLATE = "/price?requestDateTime={startDate}&productId={productId}&brandId={brandId}";

    @Autowired
    private WebTestClient webTestClient;

    static Stream<Arguments> provideTestCases() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(2020, 6, 14, 10, 0), 35455, 1, new PriceResponse(35455, 1, 1, LocalDateTime.of(2020, 6, 14, 0, 0), LocalDateTime.of(2020, 12, 31, 23, 59, 59), 35.50)),
                Arguments.of(LocalDateTime.of(2020, 6, 14, 16, 0), 35455, 1, new PriceResponse(35455, 1, 2, LocalDateTime.of(2020, 6, 14, 15, 0), LocalDateTime.of(2020, 6, 14, 18, 30), 25.45)),
                Arguments.of(LocalDateTime.of(2020, 6, 14, 21, 0), 35455, 1, new PriceResponse(35455, 1, 1, LocalDateTime.of(2020, 6, 14, 0, 0), LocalDateTime.of(2020, 12, 31, 23, 59, 59), 35.50)),
                Arguments.of(LocalDateTime.of(2020, 6, 15, 10, 0), 35455, 1, new PriceResponse(35455, 1, 3, LocalDateTime.of(2020, 6, 15, 0, 0), LocalDateTime.of(2020, 6, 15, 11, 0), 30.50)),
                Arguments.of(LocalDateTime.of(2020, 6, 16, 21, 0), 35455, 1, new PriceResponse(35455, 1, 4, LocalDateTime.of(2020, 6, 15, 16, 0), LocalDateTime.of(2020, 12, 31, 23, 59, 59), 38.95))
        );
    }


    @ParameterizedTest
    @MethodSource("provideTestCases")
    void testPriceEndpoint(final LocalDateTime requestDateTime, final int productId, final int brandId, final PriceResponse expectedResponse) {
        this.webTestClient.get()
                .uri(URL_TEMPLATE, requestDateTime, productId, brandId)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(PriceResponse.class)
                .consumeWith(response -> {
                    final PriceResponse actualResponses = response.getResponseBody();
                    assertThat(actualResponses).isEqualTo(expectedResponse);
                });
    }

}
