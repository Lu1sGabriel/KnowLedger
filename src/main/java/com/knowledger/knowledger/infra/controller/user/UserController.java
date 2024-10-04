package com.knowledger.knowledger.infra.controller.user;

import java.util.concurrent.CompletableFuture;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.knowledger.knowledger.application.commands.user.UserCreateCommand;
import com.knowledger.knowledger.application.commands.user.UserListCommand;
import com.knowledger.knowledger.application.handlers.user.UserCommandHandler;

@RestController
public class UserController {

    private final UserCommandHandler commandHandler;

    public UserController(UserCommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @PostMapping("/users")
    public CompletableFuture<ResponseEntity<Object>> createUser(@RequestBody UserCreateCommand command) {
        return commandHandler.handle(command);
    }

    @GetMapping("/users")
    public CompletableFuture<ResponseEntity<Object>> listUsers() {
        return commandHandler.handle(new UserListCommand());
    }

}