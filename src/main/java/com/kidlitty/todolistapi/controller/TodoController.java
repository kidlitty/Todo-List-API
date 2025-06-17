package com.kidlitty.todolistapi.controller;

import com.kidlitty.todolistapi.dto.CreateTodoRequest;
import com.kidlitty.todolistapi.dto.TodoDto;
import com.kidlitty.todolistapi.dto.UpdateTodoRequest;
import com.kidlitty.todolistapi.security.UserDetailsImpl;
import com.kidlitty.todolistapi.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping
    public ResponseEntity<Page<TodoDto>> getAllTodosForUser (
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            Pageable pageable) {
        Page<TodoDto> todosPage = todoService.getTodosByUsername(userDetails.getUsername(), pageable);
        return ResponseEntity.ok(todosPage);
    }

    @PostMapping
    public ResponseEntity<TodoDto> createTodo(@Valid @RequestBody CreateTodoRequest request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        TodoDto createdTodo = todoService.createTodo(request, userDetails.getUsername());
        return new ResponseEntity<>(createdTodo, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoDto> getTodoById(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        TodoDto todo = todoService.getTodoById(id, userDetails.getUsername());
        return ResponseEntity.ok(todo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoDto> updateTodo(@PathVariable Long id, @Valid @RequestBody UpdateTodoRequest request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        TodoDto updatedTodo = todoService.updateTodo(id, request, userDetails.getUsername());
        return ResponseEntity.ok(updatedTodo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        todoService.deleteTodo(id, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
}
