package com.payflow.app.application.controller;

import com.payflow.app.application.dto.user.UserRequest;
import com.payflow.app.application.dto.user.UserResponse;
import com.payflow.app.domain.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<UserResponse> cadastrar(@RequestBody UserRequest req) {
       UserResponse newUser =  service.create(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> atualizar(@PathVariable Long id, @RequestBody UserRequest req) {
       UserResponse user =  service.atualizar(id, req);
        return ResponseEntity.ok(user);
    }
}
