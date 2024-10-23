package com.knowledger.knowledger.application.usecases.user;

import com.knowledger.knowledger.commom.mapper.IMapperDTO;
import com.knowledger.knowledger.domain.user.User;
import com.knowledger.knowledger.infra.controller.user.UserDetailDTO;
import com.knowledger.knowledger.infra.gateways.user.IUserGateway;
import org.springframework.stereotype.Component;

@Component
public class UserLogin {

    private final IUserGateway iUserGateway;

    public UserLogin(IUserGateway iUserGateway) {
        this.iUserGateway = iUserGateway;
    }

    public void apply(String email, String password) throws Exception {

        iUserGateway.login(email, password);

    }

}
