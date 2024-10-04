package com.knowledger.knowledger.application.commands;

import org.springframework.context.ApplicationEvent;


public abstract class Command extends ApplicationEvent {

    private Object result;

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Command(Object source) {
        super(source);
    }
}
