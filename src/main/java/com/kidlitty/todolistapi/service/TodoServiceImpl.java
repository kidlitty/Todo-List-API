package com.kidlitty.todolistapi.service;

import com.kidlitty.todolistapi.dto.CreateTodoRequest;
import com.kidlitty.todolistapi.dto.TodoDto;
import com.kidlitty.todolistapi.dto.UpdateTodoRequest;
import com.kidlitty.todolistapi.exception.ResourceNotFoundException;
import com.kidlitty.todolistapi.model.Todo;
import com.kidlitty.todolistapi.model.User;
import com.kidlitty.todolistapi.repository.TodoRepository;
import com.kidlitty.todolistapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public TodoDto createTodo (CreateTodoRequest request, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        Todo todo = Todo.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .user(user)
                .build();

        Todo savedTodo = todoRepository.save(todo);
        return convertToDto(savedTodo);
    }

    @Override
    public Page<TodoDto> getTodosByUsername(String username, Pageable pageable) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        Page<Todo> todosPage = todoRepository.findByUser(user, pageable);
        return todosPage.map(this::convertToDto);
    }

    @Override
    public List<TodoDto> getTodosByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        List<Todo> todos = todoRepository.findByUser(user, Pageable.unpaged()).getContent();
        return todos.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public TodoDto getTodoById(Long id, String username) {
        Todo todo = getTodoAndVerifyOwnership(id, username);
        return convertToDto(todo);
    }

    @Override
    public TodoDto updateTodo(Long id, UpdateTodoRequest request, String username) {
        Todo todo = getTodoAndVerifyOwnership(id, username);

        if (request.getTitle() != null) {
            todo.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            todo.setDescription(request.getDescription());
        }
        if (request.getCompleted() != null) {
            todo.setCompleted(request.getCompleted());
        }

        Todo updatedTodo = todoRepository.save(todo);
        return convertToDto(updatedTodo);
    }

    @Override
    public void deleteTodo(Long id, String username) {
        Todo todo = getTodoAndVerifyOwnership(id, username);
        todoRepository.delete(todo);
    }

    private Todo getTodoAndVerifyOwnership(Long id, String username) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id: " + id));

        if (!todo.getUser().getUsername().equals(username)) {
            throw new ResourceNotFoundException("Todo not found with id: " + id);
        }
        return todo;
    }

    private TodoDto convertToDto(Todo todo) {
        return TodoDto.builder()
                .id(todo.getId())
                .title(todo.getTitle())
                .description(todo.getDescription())
                .completed(todo.isCompleted())
                .createdAt(todo.getCreatedAt())
                .updatedAt(todo.getUpdatedAt())
                .build();
    }
}
