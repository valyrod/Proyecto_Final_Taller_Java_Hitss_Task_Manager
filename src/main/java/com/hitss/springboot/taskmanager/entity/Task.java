package com.hitss.springboot.taskmanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "tasks")
@Schema(description = "Entidad que representa una tarea")
@JsonIgnoreProperties(value = {"user"}, ignoreUnknown = true)
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de la tarea", example = "1")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Título de la tarea", example = "Comprar videojuegos", required = true)
    private String title;

    @Schema(description = "Descripción detallada de la tarea", example = "Comprar edición Deluxe de Ghost of Yotei")
    private String description;

    @Schema(description = "Indica si la tarea está completada", example = "false")
    private boolean completed = false;
    
    @Schema(description = "Fecha y hora de creación de la tarea", example = "2025-10-31T14:15:00")
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;
    
    public Task(String title, String description, boolean completed) {
        this.title = title;
        this.description = description;
        this.completed = completed;
    }
}