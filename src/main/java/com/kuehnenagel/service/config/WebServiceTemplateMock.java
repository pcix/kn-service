package com.kuehnenagel.service.config;

import com.kuehnenagel.service.ws.model.Message;
import com.kuehnenagel.service.ws.model.Resultcode;
import org.springframework.ws.client.core.WebServiceTemplate;

public class WebServiceTemplateMock extends WebServiceTemplate {

    @Override
    public Object marshalSendAndReceive(Object requestPayload) {
        if (requestPayload instanceof Message && "success".equals(((Message) requestPayload).getValue())) {
            Resultcode resultcode = new Resultcode();
            resultcode.setValue(0);
            return resultcode;
        }
        throw new IllegalArgumentException();
    }
}
