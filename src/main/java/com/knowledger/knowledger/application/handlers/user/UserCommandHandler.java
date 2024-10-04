package com.knowledger.knowledger.application.handlers.user;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.knowledger.knowledger.application.commands.ICommand;
import com.knowledger.knowledger.application.commands.user.UserCreateCommand;
import com.knowledger.knowledger.application.commands.user.UserListCommand;

@Component
public class UserCommandHandler {

    private final Map<Class<? extends ICommand>, Function<ICommand, ResponseEntity<Object>>> commandHandlers = new HashMap<>();

    public UserCommandHandler() {
        commandHandlers.put(UserCreateCommand.class, command -> handleCreateUser((UserCreateCommand) command));
        commandHandlers.put(UserListCommand.class, command -> handleListUsers());
    }

    public ResponseEntity<Object> handle(ICommand command) {
        Function<ICommand, ResponseEntity<Object>> handler = commandHandlers.get(command.getClass());

        if (handler == null) {
            return ResponseEntity.badRequest().body("Comando desconhecido: " + command.getClass().getSimpleName());
        }

        return handler.apply(command);
    }

    private ResponseEntity<Object> handleCreateUser(UserCreateCommand command) {
        return null;

    }

    private ResponseEntity<Object> handleListUsers() {
        return null;
    }

}