package com.kidlitty.todolistapi.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateTodoRequest {

    @Size(max = 100)
    private String title;

    @Size(max = 255)
    private String description;

    private Boolean completed;
}
