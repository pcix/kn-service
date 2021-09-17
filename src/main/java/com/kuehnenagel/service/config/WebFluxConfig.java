package com.kuehnenagel.service.config;

import com.kuehnenagel.service.api.RequestHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class WebFluxConfig {

    @Bean
    public RouterFunction<ServerResponse> router(RequestHandler handler) {
        return RouterFunctions
                .route(POST("/process").and(accept(MediaType.APPLICATION_JSON)), handler::process);
    }
}
