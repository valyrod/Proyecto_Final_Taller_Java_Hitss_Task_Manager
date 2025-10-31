package com.hitss.springboot.taskmanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@Schema(description = "Solicitud para crear o actualizar una tarea")
public class TaskRequest {
    @NotBlank(message = "El título no puede estar vacío")
    @Size(min = 1, max = 100, message = "El título debe tener entre 1 y 100 caracteres")
    @Schema(description = "Título de la tarea", example = "Comprar videojuegos", required = true, minLength = 1, maxLength = 100)
    private String title;

    @Schema(description = "Descripción detallada de la tarea", example = "Comprar Mario Kart", maxLength = 500)
    private String description;

    @Schema(description = "Indica si la tarea está completada", example = "false")
    private Boolean completed;
}