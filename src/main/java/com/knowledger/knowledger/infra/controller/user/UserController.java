package com.knowledger.knowledger.infra.controller.user;

import com.knowledger.knowledger.application.usecases.user.UserChangePassword;
import com.knowledger.knowledger.application.usecases.user.UserCreate;
import com.knowledger.knowledger.application.usecases.user.UserGetById;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserCreate _userCreate;
    private final UserChangePassword _userChangePassword;
    private final UserGetById _userGetById;

    private final AuthenticationManager _authenticationManager;

    public UserController(UserCreate userCreate, UserChangePassword userChangePassword, UserGetById userGetById, AuthenticationManager authenticationManager) {
        _userCreate = userCreate;
        _userChangePassword = userChangePassword;
        _userGetById = userGetById;
        _authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDetailDTO> create(@RequestBody UserRegisterDTO dto, UriComponentsBuilder uriBuilder) throws Exception {
        var userDetailDto = _userCreate.apply(dto);
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(userDetailDto.getId()).toUri();
        return ResponseEntity.created(uri).body(userDetailDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDetailDTO> getById(@PathVariable UUID id) throws Exception {
        var userDetailDto = _userGetById.apply(id);
        return ResponseEntity.ok(userDetailDto);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<UserDetailDTO> changePassword(@RequestBody UserChangePasswordDTO dto) throws Exception {
        var userDetailDto = _userChangePassword.apply(dto);
        return ResponseEntity.ok(userDetailDto);
    }

    @PostMapping("/login")
    public String login(@RequestBody UserLoginDTO dto) {
        try {
            Authentication authentication = _authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return "Login efetuado com sucesso!";
        } catch (AuthenticationException e) {
            return "Login failed: " + e.getMessage();
        }
    }

}
