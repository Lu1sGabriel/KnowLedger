package com.knowledger.knowledger.infra.controller;

import com.knowledger.knowledger.application.commands.user.UserListCommand;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final ApplicationEventPublisher eventPublisher;

    public UserController(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @PostMapping
    public ResponseEntity<Object> list(@RequestBody UserListCommand command){
        eventPublisher.publishEvent(command);
        return ResponseEntity.ok(command.getResult());
    }

}
