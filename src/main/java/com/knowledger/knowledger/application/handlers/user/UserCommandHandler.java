package com.knowledger.knowledger.application.handlers.user;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.knowledger.knowledger.application.commands.ICommand;
import com.knowledger.knowledger.application.commands.user.UserCreateCommand;
import com.knowledger.knowledger.application.commands.user.UserListCommand;

import java.lang.reflect.Method;
import java.util.concurrent.CompletableFuture;

@Component
public class UserCommandHandler {

    @SuppressWarnings("unchecked")
    public CompletableFuture<ResponseEntity<Object>> handle(ICommand command) {
        try {
            // Cria o nome do método com base no tipo do comando (ex.: UserCreateCommand -> handleUserCreate)
            String methodName = "handle" + command.getClass().getSimpleName().replace("Command", "");

            // Procura o método no próprio UserCommandHandler
            Method method = this.getClass().getDeclaredMethod(methodName, command.getClass());

            // Invoca o método e obtém o resultado
            Object result = method.invoke(this, command);

            // Verifica se o resultado é do tipo esperado
            if (result instanceof CompletableFuture) {
                return (CompletableFuture<ResponseEntity<Object>>) result;
            } else {
                return CompletableFuture.completedFuture(
                        ResponseEntity.status(500).body("Erro interno: tipo de retorno inválido do manipulador."));
            }
        } catch (NoSuchMethodException e) {
            return CompletableFuture.completedFuture(
                    ResponseEntity.badRequest().body("Comando desconhecido: " + command.getClass().getSimpleName()));
        } catch (Exception e) {
            return CompletableFuture.completedFuture(
                    ResponseEntity.status(500).body("Erro ao processar comando: " + e.getMessage()));
        }
    }

    // Método para criar usuário
    @SuppressWarnings("unused")
    private CompletableFuture<ResponseEntity<Object>> handleUserCreate(UserCreateCommand command) {
        return CompletableFuture.supplyAsync(() -> {
            return null;
        });
    }

    // Método para listar usuários
    @SuppressWarnings("unused")
    private CompletableFuture<ResponseEntity<Object>> handleUserList(UserListCommand command) {
        return CompletableFuture.supplyAsync(() -> {
            return null;
        });
    }

}