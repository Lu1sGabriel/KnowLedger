package com.knowledger.knowledger.infra.controller.user;

import com.knowledger.knowledger.application.usecases.user.UserChangePassword;
import com.knowledger.knowledger.application.usecases.user.UserRegister;
import com.knowledger.knowledger.application.usecases.user.UserGetById;
import com.knowledger.knowledger.application.usecases.user.UserLogin;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRegister _userRegister;
    private final UserChangePassword _userChangePassword;
    private final UserGetById _userGetById;
    private final UserLogin _userLogin;

    public UserController(UserRegister userRegister, UserChangePassword userChangePassword, UserGetById userGetById, UserLogin userLogin) {
        _userRegister = userRegister;
        _userChangePassword = userChangePassword;
        _userGetById = userGetById;
        _userLogin = userLogin;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDetailDTO> create(@RequestBody UserRegisterDTO dto, UriComponentsBuilder uriBuilder) throws Exception {
        var userDetailDto = _userRegister.apply(dto);
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
    public ResponseEntity<UserTokenAuthenticationDTO> login(@RequestBody UserLoginDTO dto) {
        var token = _userLogin.apply(dto.getEmail(), dto.getPassword());
        return ResponseEntity.ok(token);
    }

}
