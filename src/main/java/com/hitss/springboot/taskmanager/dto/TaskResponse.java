package com.hitss.springboot.taskmanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Schema(description = "Respuesta que representa una tarea")
@Data
public class TaskResponse {
    @Schema(description = "ID único de la tarea", example = "1")
    private Long id;

    @Schema(description = "Título de la tarea", example = "Comprar videojuegos", required = true)
    private String title;

    @Schema(description = "Descripción detallada de la tarea", example = "Comprar Batman")
    private String description;

    @Schema(description = "Indica si la tarea está completada", example = "false")
    private Boolean completed = false;
    
    @Schema(description = "Fecha y hora de creación de la tarea", example = "2025-10-31T14:15:00")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Schema(description = "Usuario propietario de la tarea")
    private UserSummary user;
    
    public TaskResponse(Long id, String title, String description, Boolean completed, LocalDateTime createdAt, UserSummary user) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = completed;
        this.createdAt = createdAt;
        this.user = user;
    }
    
    @Schema(description = "Resumen del usuario propietario de la tarea")
    @Data
    public static class UserSummary {
        @Schema(description = "ID único del usuario", example = "1")
        private Long id;
        
        @Schema(description = "Nombre de usuario", example = "admin")
        private String username;
        
        @Schema(description = "Email del usuario", example = "admin@example.com")
        private String email;
        
        public UserSummary(Long id, String username, String email) {
            this.id = id;
            this.username = username;
            this.email = email;
        }
    }
}