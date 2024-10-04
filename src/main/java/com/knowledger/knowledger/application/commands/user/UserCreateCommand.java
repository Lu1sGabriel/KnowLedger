package com.knowledger.knowledger.application.commands.user;

import com.knowledger.knowledger.application.commands.ICommand;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserCreateCommand implements ICommand {
    private String name;
    private String email;
}