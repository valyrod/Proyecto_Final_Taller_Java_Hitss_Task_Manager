package com.hitss.springboot.taskmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.hitss.springboot.taskmanager.entity.ERole;
import com.hitss.springboot.taskmanager.entity.Role;
import com.hitss.springboot.taskmanager.entity.User;
import com.hitss.springboot.taskmanager.repository.RoleRepository;
import com.hitss.springboot.taskmanager.repository.UserRepository;

@SpringBootApplication
public class TaskManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskManagerApplication.class, args);
	}

	@Bean
	CommandLineRunner init(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder encoder) {
		return args -> {
			if (roleRepository.findByName(ERole.ROLE_USER).isEmpty()) {
				roleRepository.save(new Role(ERole.ROLE_USER));
			}
			if (roleRepository.findByName(ERole.ROLE_ADMIN).isEmpty()) {
				roleRepository.save(new Role(ERole.ROLE_ADMIN));
			}

			if (!userRepository.existsByUsername("admin")) {
				User admin = new User("admin", "admin@example.com", encoder.encode("admin123"));
				admin.getRoles().add(roleRepository.findByName(ERole.ROLE_ADMIN)
						.orElseThrow(() -> new RuntimeException("ROLE_ADMIN no encontrado")));
				userRepository.save(admin);
			}
		};
	}
}
