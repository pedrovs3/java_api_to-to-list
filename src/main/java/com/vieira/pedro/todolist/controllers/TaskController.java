package com.vieira.pedro.todolist.controllers;

import com.vieira.pedro.todolist.models.TaskModel;
import com.vieira.pedro.todolist.repository.ITaskRepository;
import com.vieira.pedro.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/task")
public class TaskController {
    private final ITaskRepository taskRepository;

    public TaskController(ITaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    @PostMapping(
            value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request) {
        taskModel.setUserId((UUID) request.getAttribute("userId"));

        var currentDate = LocalDateTime.now();
        if (currentDate.isAfter(taskModel.getDueDate()) || currentDate.isEqual(taskModel.getDueDate())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Due date must be after current date");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(this.taskRepository.save(taskModel));
    }

    @GetMapping(
            value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity list(HttpServletRequest request) {
        var id = (UUID) request.getAttribute("userId");
        return ResponseEntity.status(HttpStatus.OK).body(this.taskRepository.findAllByUserId(id));
    }

    @PutMapping(
            value = "/{id}"
    )
    public ResponseEntity update(@RequestBody TaskModel taskModel, @PathVariable String id, HttpServletRequest request) {
        var task = this.taskRepository.findByStringId(id);
        UUID idUser = (UUID) request.getAttribute("userId");
        if (task == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        }
        if (!task.getUserId().equals(idUser)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        Utils.copyNonNullProperties(taskModel, task);

        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.taskRepository.save(task));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
