package com.knowledger.knowledger.application.commands.user;

import com.knowledger.knowledger.application.commands.Command;

public class UserCreateCommand extends Command {
    private String name;
    private String email;

    public UserCreateCommand(Object source, String name, String email) {
        super(source);
        this.name = name;
        this.email = email;
    }
}
