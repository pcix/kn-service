package com.kuehnenagel.service.ws;

import com.kuehnenagel.service.ws.WebServiceClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
public class WebServiceClientTest {

    @Autowired
    private WebServiceClient client;

    @Test
    void success() {
        Mono<String> source = Mono.just("success");
        StepVerifier.create(client.call(source))
                .expectNext(0)
                .verifyComplete();
    }

    @Test
    void failed() {
        Mono<String> source = Mono.just("failed");
        StepVerifier.create(client.call(source))
                .expectError(IllegalArgumentException.class)
                .verify();
    }
}
