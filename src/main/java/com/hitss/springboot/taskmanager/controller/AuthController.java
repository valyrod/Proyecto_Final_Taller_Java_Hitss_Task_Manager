package com.hitss.springboot.taskmanager.controller;

import com.hitss.springboot.taskmanager.dto.JwtResponse;
import com.hitss.springboot.taskmanager.dto.LoginRequest;
import com.hitss.springboot.taskmanager.dto.MessageResponse;
import com.hitss.springboot.taskmanager.dto.SignupRequest;
import com.hitss.springboot.taskmanager.entity.ERole;
import com.hitss.springboot.taskmanager.entity.Role;
import com.hitss.springboot.taskmanager.entity.User;
import com.hitss.springboot.taskmanager.repository.RoleRepository;
import com.hitss.springboot.taskmanager.repository.UserRepository;
import com.hitss.springboot.taskmanager.security.jwt.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Autenticación", description = "Endpoints para la autenticación de usuarios")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, 
                          RoleRepository roleRepository, PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/signin")
    @Operation(summary = "Iniciar sesión", description = "Autenticar un usuario y obtener un token JWT")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Inicio de sesión exitoso",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = JwtResponse.class),
            examples = @ExampleObject(
                value = "{\n  \"token\": \"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY4MjQ3MDg3MCwiZXhwIjoxNjgyNDc0NDcwfQ.token\",\n  \"type\": \"Bearer\",\n  \"username\": \"admin\",\n  \"roles\": [\"ROLE_ADMIN\"]\n}"
            ))),
        @ApiResponse(responseCode = "401", description = "Credenciales inválidas",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(
                value = "{\n  \"timestamp\": \"2025-10-31T14:15:00.000+00:00\",\n  \"status\": 401,\n  \"error\": \"Unauthorized\",\n  \"path\": \"/api/auth/signin\"\n}"
            ))),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(
                value = "{\n  \"timestamp\": \"2025-10-31T14:15:00.000+00:00\",\n  \"status\": 400,\n  \"error\": \"Bad Request\",\n  \"path\": \"/api/auth/signin\",\n  \"message\": \"Nombre de usuario o contraseña no pueden estar vacíos\"\n}"
            )))
    })
    public ResponseEntity<?> authenticateUser(
        @Parameter(description = "Credenciales de inicio de sesión", required = true) 
        @Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        org.springframework.security.core.userdetails.User userDetails = 
            (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), roles));
    }

    @PostMapping("/signup")
    @Operation(summary = "Registrar usuario", description = "Crear un nuevo usuario en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario registrado exitosamente",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = MessageResponse.class),
            examples = @ExampleObject(
                value = "{\n  \"message\": \"Usuario registrado exitosamente!\"\n}"
            ))),
        @ApiResponse(responseCode = "400", description = "Nombre de usuario o email ya en uso",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(
                value = "{\n  \"message\": \"Error: El nombre de usuario ya está en uso!\"\n}"
            ))),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta",
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(
                value = "{\n  \"timestamp\": \"2025-10-31T14:15:00.000+00:00\",\n  \"status\": 400,\n  \"error\": \"Bad Request\",\n  \"path\": \"/api/auth/signup\",\n  \"message\": \"Nombre de usuario debe tener entre 3 y 20 caracteres\"\n}"
            )))
    })
    public ResponseEntity<?> registerUser(
        @Parameter(description = "Datos de registro del nuevo usuario", required = true)
        @Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: El nombre de usuario ya está en uso!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: El Email ya está en uso!"));
        }
        
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role 'USER' no encontrado."));
        roles.add(userRole);

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("Usuario registrado exitosamente!"));
    }
}