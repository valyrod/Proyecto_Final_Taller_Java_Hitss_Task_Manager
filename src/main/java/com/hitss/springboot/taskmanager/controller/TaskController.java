package com.hitss.springboot.taskmanager.controller;

import com.hitss.springboot.taskmanager.dto.MessageResponse;
import com.hitss.springboot.taskmanager.entity.Task;
import com.hitss.springboot.taskmanager.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Tareas", description = "Endpoints para la gestión de tareas")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Crear una nueva tarea", description = "Crea una nueva tarea para el usuario autenticado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tarea creada exitosamente",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Task.class),
            examples = @ExampleObject(
                value = "{\n  \"id\": 1,\n  \"title\": \"Comprar videojuegos\",\n  \"description\": \"Comprar Ghost of Yotei\",\n  \"completed\": false,\n  \"createdAt\": \"2025-10-31T14:15:00\"\n}"
            ))),
        @ApiResponse(responseCode = "401", description = "No autorizado - Token JWT no válido o ausente",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(
                value = "{\n  \"timestamp\": \"2025-10-31T14:15:00.000+00:00\",\n  \"status\": 401,\n  \"error\": \"Unauthorized\",\n  \"path\": \"/api/tasks\"\n}"
            ))),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta - Datos de tarea inválidos",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(
                value = "{\n  \"timestamp\": \"2025-10-31T14:15:00.000+00:00\",\n  \"status\": 400,\n  \"error\": \"Bad Request\",\n  \"path\": \"/api/tasks\",\n  \"message\": \"El título no puede estar vacío\"\n}"
            ))),
        @ApiResponse(responseCode = "403", description = "Acceso prohibido - No tiene permisos suficientes",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(
                value = "{\n  \"timestamp\": \"2025-10-31T14:15:00.000+00:00\",\n  \"status\": 403,\n  \"error\": \"Forbidden\",\n  \"path\": \"/api/tasks\"\n}"
            )))
    })
    public ResponseEntity<Task> createTask(
        @Parameter(description = "Datos de la nueva tarea a crear", required = true) 
        @Valid @RequestBody Task task, 
        @Parameter(description = "Información de autenticación del usuario", hidden = true) 
        Authentication authentication) {
        Task createdTask = taskService.createTask(task, authentication.getName());
        return ResponseEntity.ok(createdTask);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Obtener todas las tareas", description = "Recupera todas las tareas del usuario autenticado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tareas recuperadas exitosamente",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = List.class),
            examples = @ExampleObject(
                value = "[\n  {\n    \"id\": 1,\n    \"title\": \"Comprar videojuegos\",\n    \"description\": \"Comprar Ghost of Tsushima\",\n    \"completed\": false,\n    \"createdAt\": \"2025-10-31T14:15:00\",\n    \"user\": {\n      \"id\": 1,\n      \"username\": \"admin\",\n      \"email\": \"admin@example.com\"\n    }\n  },\n  {\n    \"id\": 2,\n    \"title\": \"Reunión de equipo\",\n    \"description\": \"Reunión semanal de proyecto\",\n    \"completed\": true,\n    \"createdAt\": \"2025-10-30T09:30:00\",\n    \"user\": {\n      \"id\": 1,\n      \"username\": \"admin\",\n      \"email\": \"admin@example.com\"\n    }\n  }\n]"
            ))),
        @ApiResponse(responseCode = "401", description = "No autorizado - Token JWT no válido o ausente",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(
                value = "{\n  \"timestamp\": \"2025-10-31T14:15:00.000+00:00\",\n  \"status\": 401,\n  \"error\": \"Unauthorized\",\n  \"path\": \"/api/tasks\"\n}"
            ))),
        @ApiResponse(responseCode = "403", description = "Acceso prohibido - No tiene permisos suficientes",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(
                value = "{\n  \"timestamp\": \"2025-10-31T14:15:00.000+00:00\",\n  \"status\": 403,\n  \"error\": \"Forbidden\",\n  \"path\": \"/api/tasks\"\n}"
            )))
    })
    public ResponseEntity<List<Task>> getAllTasks(
        @Parameter(description = "Información de autenticación del usuario", hidden = true) 
        Authentication authentication) {
        List<Task> tasks = taskService.getAllTasks(authentication.getName());
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Obtener una tarea por ID", description = "Recupera una tarea específica por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tarea encontrada y recuperada exitosamente",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Task.class),
            examples = @ExampleObject(
                value = "{\n  \"id\": 1,\n  \"title\": \"Comprar videojuegos\",\n  \"description\": \"Comprar Mario Kart\",\n  \"completed\": false,\n  \"createdAt\": \"2025-10-31T14:15:00\",\n  \"user\": {\n    \"id\": 1,\n    \"username\": \"admin\",\n    \"email\": \"admin@example.com\"\n  }\n}"
            ))),
        @ApiResponse(responseCode = "401", description = "No autorizado - Token JWT no válido o ausente",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(
                value = "{\n  \"timestamp\": \"2025-10-31T14:15:00.000+00:00\",\n  \"status\": 401,\n  \"error\": \"Unauthorized\",\n  \"path\": \"/api/tasks/1\"\n}"
            ))),
        @ApiResponse(responseCode = "403", description = "Acceso prohibido - No tiene permisos suficientes o tarea no pertenece al usuario",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(
                value = "{\n  \"timestamp\": \"2025-10-31T14:15:00.000+00:00\",\n  \"status\": 403,\n  \"error\": \"Forbidden\",\n  \"path\": \"/api/tasks/1\"\n}"
            ))),
        @ApiResponse(responseCode = "404", description = "Tarea no encontrada",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(
                value = "{\n  \"timestamp\": \"2025-10-31T14:15:00.000+00:00\",\n  \"status\": 404,\n  \"error\": \"Not Found\",\n  \"path\": \"/api/tasks/999\"\n}"
            )))
    })
    public ResponseEntity<Task> getTaskById(
        @Parameter(description = "ID de la tarea a buscar", example = "1", required = true) 
        @PathVariable Long id, 
        @Parameter(description = "Información de autenticación del usuario", hidden = true) 
        Authentication authentication) {
        Task task = taskService.getTaskById(id, authentication.getName());
        return ResponseEntity.ok(task);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Actualizar una tarea", description = "Actualiza una tarea existente por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tarea actualizada exitosamente",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Task.class),
            examples = @ExampleObject(
                value = "{\n  \"id\": 1,\n  \"title\": \"Comprar videojuegos actualizado\",\n  \"description\": \"Comprar edicion deluxe Ghost of Yotei\",\n  \"completed\": true,\n  \"createdAt\": \"2025-10-31T14:15:00\",\n  \"user\": {\n    \"id\": 1,\n    \"username\": \"admin\",\n    \"email\": \"admin@example.com\"\n  }\n}"
            ))),
        @ApiResponse(responseCode = "401", description = "No autorizado - Token JWT no válido o ausente",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(
                value = "{\n  \"timestamp\": \"2025-10-31T14:15:00.000+00:00\",\n  \"status\": 401,\n  \"error\": \"Unauthorized\",\n  \"path\": \"/api/tasks/1\"\n}"
            ))),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta - Datos de tarea inválidos",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(
                value = "{\n  \"timestamp\": \"2025-10-31T14:15:00.000+00:00\",\n  \"status\": 400,\n  \"error\": \"Bad Request\",\n  \"path\": \"/api/tasks/1\",\n  \"message\": \"El título no puede estar vacío\"\n}"
            ))),
        @ApiResponse(responseCode = "403", description = "Acceso prohibido - No tiene permisos suficientes o tarea no pertenece al usuario",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(
                value = "{\n  \"timestamp\": \"2025-10-31T14:15:00.000+00:00\",\n  \"status\": 403,\n  \"error\": \"Forbidden\",\n  \"path\": \"/api/tasks/1\"\n}"
            ))),
        @ApiResponse(responseCode = "404", description = "Tarea no encontrada",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(
                value = "{\n  \"timestamp\": \"2025-10-31T14:15:00.000+00:00\",\n  \"status\": 404,\n  \"error\": \"Not Found\",\n  \"path\": \"/api/tasks/999\"\n}"
            )))
    })
    public ResponseEntity<Task> updateTask(
        @Parameter(description = "ID de la tarea a actualizar", example = "1", required = true) 
        @PathVariable Long id, 
        @Parameter(description = "Datos actualizados de la tarea", required = true) 
        @Valid @RequestBody Task taskDetails, 
        @Parameter(description = "Información de autenticación del usuario", hidden = true) 
        Authentication authentication) {
        Task updatedTask = taskService.updateTask(id, taskDetails, authentication.getName());
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Eliminar una tarea", description = "Elimina una tarea existente por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tarea eliminada exitosamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = MessageResponse.class),
            examples = @ExampleObject(
                value = "{\n  \"message\": \"Tarea eliminada exitosamente\"\n}"
            ))),
        @ApiResponse(responseCode = "401", description = "No autorizado - Token JWT no válido o ausente",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(
                value = "{\n  \"timestamp\": \"2025-10-31T14:15:00.000+00:00\",\n  \"status\": 401,\n  \"error\": \"Unauthorized\",\n  \"path\": \"/api/tasks/1\"\n}"
            ))),
        @ApiResponse(responseCode = "403", description = "Acceso prohibido - No tiene permisos suficientes o tarea no pertenece al usuario",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(
                value = "{\n  \"timestamp\": \"2025-10-31T14:15:00.000+00:00\",\n  \"status\": 403,\n  \"error\": \"Forbidden\",\n  \"path\": \"/api/tasks/1\"\n}"
            ))),
        @ApiResponse(responseCode = "404", description = "Tarea no encontrada",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(
                value = "{\n  \"timestamp\": \"2025-10-31T14:15:00.000+00:00\",\n  \"status\": 404,\n  \"error\": \"Not Found\",\n  \"path\": \"/api/tasks/999\"\n}"
            )))
    })
    public ResponseEntity<?> deleteTask(
        @Parameter(description = "ID de la tarea a eliminar", example = "1", required = true) 
        @PathVariable Long id, 
        @Parameter(description = "Información de autenticación del usuario", hidden = true) 
        Authentication authentication) {
        taskService.deleteTask(id, authentication.getName());
        return ResponseEntity.ok(new MessageResponse("Tarea eliminada exitosamente"));
    }
}