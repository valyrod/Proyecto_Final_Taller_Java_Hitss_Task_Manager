package com.hitss.springboot.taskmanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Respuesta genérica con mensaje")
public class MessageResponse {
    @Schema(description = "Mensaje de respuesta", example = "Operación realizada exitosamente")
    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }
}