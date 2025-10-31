package com.hitss.springboot.taskmanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Solicitud de registro de nuevo usuario")
public class SignupRequest {
    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    @Size(min = 3, max = 20, message = "El nombre de usuario debe tener entre 3 y 20 caracteres")
    @Schema(description = "Nombre de usuario deseado", example = "nuevo_usuario", required = true, minLength = 3, maxLength = 20)
    private String username;

    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El email no es válido")
    @Size(max = 50, message = "El email no puede tener más de 50 caracteres")
    @Schema(description = "Correo electrónico del usuario", example = "usuario@ejemplo.com", required = true, maxLength = 50)
    private String email;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 6, max = 40, message = "La contraseña debe tener entre 6 y 40 caracteres")
    @Schema(description = "Contraseña del usuario", example = "contraseña123", required = true, minLength = 6, maxLength = 40)
    private String password;
}