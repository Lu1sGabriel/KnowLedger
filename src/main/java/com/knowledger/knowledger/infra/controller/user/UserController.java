package com.knowledger.knowledger.infra.controller.user;

import com.knowledger.knowledger.application.usecases.user.UserChangePassword;
import com.knowledger.knowledger.application.usecases.user.UserCreate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserCreate userCreate;
    private UserChangePassword userChangePassword;

    private final AuthenticationManager _authenticationManager;

    public UserController(AuthenticationManager authenticationManager) {
        this._authenticationManager = authenticationManager;
    }

    @PostMapping("/create")
    public ResponseEntity<UserDetailDTO> create(@RequestBody UserCreateDTO dto) throws Exception {
        var userDetailDto = userCreate.apply(dto);
        return ResponseEntity.ok(userDetailDto);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<UserDetailDTO> changePassword(@RequestBody UserChangePasswordDTO dto) throws Exception {
        var userDetailDto = userChangePassword.apply(dto);
        return ResponseEntity.ok(userDetailDto);
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password) {
        try {
            Authentication authentication = _authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return "Login efetuado com sucesso!";
        } catch (AuthenticationException e) {
            return "Login failed: " + e.getMessage();
        }
    }

}
