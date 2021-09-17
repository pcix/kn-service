package com.kuehnenagel.service.api;

import com.kuehnenagel.service.api.model.Request;
import com.kuehnenagel.service.api.model.Response;
import com.kuehnenagel.service.ws.WebServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class RequestHandler {

    private final WebServiceClient wsClient;
    private final Validator validator;

    private final static String MESSAGE_ID = "ID";

    public Mono<ServerResponse> process(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(Request.class)
                .doOnNext(this::validate)
                .flatMap(r -> Mono.just(r.getMessage())
                        .transform(wsClient::call)
                        .transformDeferredContextual((resultCode, ctx) ->
                                resultCode.flatMap(c -> ServerResponse.ok()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(new Response(ctx.getOrDefault(MESSAGE_ID, 0)))))
                        .contextWrite(ctx -> ctx.put(MESSAGE_ID, r.getId())));
    }

    private void validate(Request request) {
        Errors errors = new BeanPropertyBindingResult(request, "request");
        validator.validate(request, errors);
        if (errors.hasErrors()) {
            throw new ServerWebInputException(errors.toString());
        }
    }
}
