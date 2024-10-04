package com.knowledger.knowledger.application.handlers.user;

import java.lang.reflect.Method;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.knowledger.knowledger.application.commands.ICommand;
import com.knowledger.knowledger.application.commands.user.UserCreateCommand;
import com.knowledger.knowledger.application.commands.user.UserListCommand;

@Component
public class UserCommandHandler {

    @SuppressWarnings("unchecked")
    public ResponseEntity<Object> handle(ICommand command) {
        try {
            // Procura o método chamado "handle" que aceita o tipo do comando como parâmetro
            Method method = this.getClass().getDeclaredMethod("handle", command.getClass());

            // Invoca o método e obtém o resultado
            Object result = method.invoke(this, command);

            // Verifica se o resultado é do tipo ResponseEntity
            if (result instanceof ResponseEntity) {
                return (ResponseEntity<Object>) result;
            } else {
                return ResponseEntity.status(500).body("Erro interno: tipo de retorno inválido do manipulador.");
            }
        } catch (NoSuchMethodException e) {
            return ResponseEntity.badRequest().body("Comando desconhecido: " + command.getClass().getSimpleName());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao processar comando: " + e.getMessage());
        }
    }

    // Método para criar usuário
    @SuppressWarnings("unused")
    private ResponseEntity<Object> handle(UserCreateCommand command) {
        return null;
    }

    // Método para listar usuários
    @SuppressWarnings("unused")
    private ResponseEntity<Object> handle(UserListCommand command) {
        return null;
    }

}