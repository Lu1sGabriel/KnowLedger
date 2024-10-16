package com.knowledger.knowledger.infra.controller.user;

import com.knowledger.knowledger.application.usecases.user.UserChangePassword;
import com.knowledger.knowledger.application.usecases.user.UserCreate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserCreate userCreate;
    private UserChangePassword userChangePassword;

    @PostMapping
    public ResponseEntity<UserDetailDTO> create(@RequestBody UserCreateDTO dto) {
        var userDetailDto = userCreate.apply(dto);
        return ResponseEntity.ok(userDetailDto);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<UserDetailDTO> changePassword(@RequestBody UserChangePasswordDTO dto) throws Exception {
        var userDetailDto = userChangePassword.apply(dto);
        return ResponseEntity.ok(userDetailDto);
    }

}
