package com.knowledger.knowledger.application.handlers.user;

import com.knowledger.knowledger.application.commands.user.UserCreateCommand;
import com.knowledger.knowledger.application.commands.user.UserListCommand;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class UserCommandHandler {

    @EventListener
    public void handleAsync(UserCreateCommand command) {
        System.out.println("Handling UserCreateCommand for: ");
    }

    @EventListener
    public void handleAsync(UserListCommand command) {
        System.out.println("Handling UserListCommand for: ");
        command.setResult("asasasa");
    }

}
