package com.kuehnenagel.service.config;

import com.kuehnenagel.service.ws.model.Message;
import com.kuehnenagel.service.ws.model.Resultcode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WebServiceTemplateMockTest {

    private final WebServiceTemplateMock template = new WebServiceTemplateMock();

    @Test
    void zeroResultCode() {
        Message message = new Message();
        message.setValue("success");
        Object result = template.marshalSendAndReceive(message);
        assertTrue(result instanceof Resultcode);
        assertEquals(0, ((Resultcode) result).getValue());
    }

    @Test
    void failed_NullMessage() {
        assertThrows(IllegalArgumentException.class, () -> template.marshalSendAndReceive(null));
    }

    @Test
    void failed_UnknownMessageType() {
        Resultcode message = new Resultcode();
        message.setValue(0);
        assertThrows(IllegalArgumentException.class, () -> template.marshalSendAndReceive(message));
    }
}
