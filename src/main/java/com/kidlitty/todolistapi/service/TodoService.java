package com.kidlitty.todolistapi.service;

import com.kidlitty.todolistapi.dto.TodoDto;
import com.kidlitty.todolistapi.dto.CreateTodoRequest;
import com.kidlitty.todolistapi.dto.UpdateTodoRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TodoService {
    TodoDto createTodo(CreateTodoRequest request, String username);
    TodoDto getTodoById(Long id, String username);
    TodoDto updateTodo(Long id, UpdateTodoRequest request, String username);
    void deleteTodo(Long id, String username);
    Page<TodoDto> getTodosByUsername(String username, Pageable pageable);
    List<TodoDto> getTodosByUsername(String username);

}
