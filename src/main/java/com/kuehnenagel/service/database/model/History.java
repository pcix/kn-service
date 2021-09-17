package com.kuehnenagel.service.database.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class History {

    @Id
    private Long id;

    private Integer messageId;

    private String message;

    public History(Integer messageId, String message) {
        this.messageId = messageId;
        this.message = message;
    }
}
