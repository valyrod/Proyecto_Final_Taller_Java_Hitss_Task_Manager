package com.hitss.springboot.taskmanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.List;

@Data
@Schema(description = "Respuesta de autenticación con token JWT")
public class JwtResponse {
    @Schema(description = "Token JWT de autenticación", example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY4MjQ3MDg3MCwiZXhwIjoxNjgyNDc0NDcwfQ.token")
    private String token;
    
    @Schema(description = "Tipo de token", example = "Bearer")
    private String type = "Bearer";
    
    @Schema(description = "Nombre de usuario autenticado", example = "admin")
    private String username;
    
    @Schema(description = "Roles del usuario", example = "[\"ROLE_ADMIN\"]")
    private List<String> roles;

    public JwtResponse(String accessToken, String username, List<String> roles) {
        this.token = accessToken;
        this.username = username;
        this.roles = roles;
    }
}