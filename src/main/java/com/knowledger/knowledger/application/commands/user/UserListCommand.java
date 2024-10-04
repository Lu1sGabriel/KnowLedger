package com.knowledger.knowledger.application.commands.user;

import com.knowledger.knowledger.application.commands.Command;

public class UserListCommand extends Command {

    private String name;

    public UserListCommand() {
        super("UserController");
    }

    public UserListCommand(Object source, String name) {
        super(source);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
