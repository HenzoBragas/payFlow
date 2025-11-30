package com.payflow.app.application.controllers;

import com.payflow.app.application.dto.user.UserRequest;
import com.payflow.app.application.dto.user.UserResponse;
import com.payflow.app.application.service.UserService;
import com.payflow.app.infrastructure.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> cadastrar(@RequestBody  UserRequest req) {
        service.create(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> atualizar(@PathVariable Long id, @RequestBody  UserRequest req) {
       UserResponse user =  service.atualizar(id, req);
        return ResponseEntity.ok(user);
    }
}
