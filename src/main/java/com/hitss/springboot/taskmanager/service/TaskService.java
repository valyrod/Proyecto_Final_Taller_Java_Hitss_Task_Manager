package com.hitss.springboot.taskmanager.service;

import com.hitss.springboot.taskmanager.entity.Task;
import com.hitss.springboot.taskmanager.entity.User;
import com.hitss.springboot.taskmanager.exception.ResourceNotFoundException;
import com.hitss.springboot.taskmanager.exception.UnauthorizedException;
import com.hitss.springboot.taskmanager.repository.TaskRepository;
import com.hitss.springboot.taskmanager.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Task createTask(Task task, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + username));
        task.setUser(user);
        return taskRepository.save(task);
    }

    @Transactional(readOnly = true)
    public List<Task> getAllTasks(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + username));
        
        // verificar si el usuario tiene rol ADMIN
        boolean isAdmin = user.getRoles().stream()
                .anyMatch(role -> role.getName() == com.hitss.springboot.taskmanager.entity.ERole.ROLE_ADMIN);
        
        if (isAdmin) {
            // ADMIN puede ver todas las tareas
            return taskRepository.findAll();
        } else {
            // USER solo puede ver sus propias tareas
            return taskRepository.findByUserId(user.getId());
        }
    }

    @Transactional(readOnly = true)
    public Task getTaskById(Long id, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + username));
        
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarea no encontrada con id: " + id));
        
        // verificar si el usuario tiene rol ADMIN
        boolean isAdmin = user.getRoles().stream()
                .anyMatch(role -> role.getName() == com.hitss.springboot.taskmanager.entity.ERole.ROLE_ADMIN);
        
        // si no es admin, verificar que la tarea pertenezca al usuario
        if (!isAdmin && !task.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedException("No tienes permiso para acceder a esta tarea");
        }
        
        return task;
    }

    @Transactional
    public Task updateTask(Long id, Task taskDetails, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + username));
        
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarea no encontrada con id: " + id));
        
        // verificar si el usuario tiene rol ADMIN
        boolean isAdmin = user.getRoles().stream()
                .anyMatch(role -> role.getName() == com.hitss.springboot.taskmanager.entity.ERole.ROLE_ADMIN);
        
        // si no es admin, verificar que la tarea pertenezca al usuario
        if (!isAdmin && !task.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedException("No tienes permiso para actualizar esta tarea");
        }
        
        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setCompleted(taskDetails.isCompleted());
        
        return taskRepository.save(task);
    }

    @Transactional
    public void deleteTask(Long id, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + username));
        
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarea no encontrada con id: " + id));
        
        // verificar si el usuario tiene rol ADMIN
        boolean isAdmin = user.getRoles().stream()
                .anyMatch(role -> role.getName() == com.hitss.springboot.taskmanager.entity.ERole.ROLE_ADMIN);
        
        // si no es admin, verificar que la tarea pertenezca al usuario
        if (!isAdmin && !task.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedException("No tienes permiso para eliminar esta tarea");
        }
        
        taskRepository.delete(task);
    }
}