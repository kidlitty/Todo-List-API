package com.kidlitty.todolistapi.repository;

import com.kidlitty.todolistapi.model.Todo;
import com.kidlitty.todolistapi.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    Page<Todo> findByUser(User user, Pageable pageable);
}
