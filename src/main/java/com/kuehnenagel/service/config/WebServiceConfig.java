package com.kuehnenagel.service.config;

import com.kuehnenagel.service.ws.WebServiceClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;

@Configuration
public class WebServiceConfig {

    @Value("${ws.uri}")
    private String uri;

    @Bean
    public WebServiceTemplate webServiceTemplate() {
        return new WebServiceTemplateMock();
    }

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.kuehnenagel.service.ws.model");
        return marshaller;
    }

    @Bean
    public WebServiceClient client(WebServiceTemplate webServiceTemplate, Jaxb2Marshaller marshaller) {
        WebServiceClient client = new WebServiceClient();
        client.setDefaultUri(uri);
        client.setWebServiceTemplate(webServiceTemplate);
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }
}