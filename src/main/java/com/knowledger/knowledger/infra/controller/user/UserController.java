package com.knowledger.knowledger.infra.controller.user;

import com.knowledger.knowledger.application.usecases.user.UserChangePassword;
import com.knowledger.knowledger.application.usecases.user.UserCreate;
import com.knowledger.knowledger.application.usecases.user.UserGetById;
import com.knowledger.knowledger.application.usecases.user.UserLogin;
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
    private final UserLogin _userLogin;

    private final AuthenticationManager _authenticationManager;

    public UserController(UserCreate userCreate, UserChangePassword userChangePassword, UserGetById userGetById, UserLogin userLogin, AuthenticationManager authenticationManager) {
        _userCreate = userCreate;
        _userChangePassword = userChangePassword;
        _userGetById = userGetById;
        _userLogin = userLogin;
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
    public ResponseEntity login(@RequestBody UserLoginDTO dto) throws Exception {
        _userLogin.apply(dto.getEmail(), dto.getPassword());
        return ResponseEntity.ok("Login efetuado com sucesso!");
    }

}
