package com.kuehnenagel.service.ws;

import com.kuehnenagel.service.ws.model.Message;
import com.kuehnenagel.service.ws.model.Resultcode;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

// TODO: Use something ancient like Axis because neither CFX nor JAX-WS supports RPC/Encoded type
public class WebServiceClient extends WebServiceGatewaySupport {

    public Mono<Integer> call(Mono<String> message) {
        return message
                .map(m -> {
                    Message request = new Message();
                    request.setValue(m);
                    return request;
                })
                .flatMap(request -> Mono.fromCallable(() ->
                        (Resultcode) getWebServiceTemplate().marshalSendAndReceive(request)))
                .publishOn(Schedulers.boundedElastic())
                .map(Resultcode::getValue);
    }
}
