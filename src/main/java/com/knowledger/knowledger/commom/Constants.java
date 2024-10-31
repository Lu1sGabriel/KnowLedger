package com.knowledger.knowledger.commom;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Constants {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static class UserRole {
        public static final Long ADMIN = 1L;
        public static final Long USER = 2L;
    }

    public static String toDateString(LocalDateTime dateTime) {
        if (Objects.isNull(dateTime)) {
            return null;
        }
        return dateTime.format(formatter);
    }

    public static class Security {
        public static final String AUTHORIZATION_HEADER = "Authorization";
        public static final String BEARER_PREFIX = "Bearer ";
    }

    public static class JWT {
        public static final String ISSUER = "knowledger";
        public static final String ROLE_CLAIM = "role";
        public static final String EMAIL_CLAIM = "email";
    }

    public static class NoRequiredAuthorizedPath {
        public static final String USER_LOGIN = "/users/login/**";
        public static final String USER_REGISTER = "/users/register/**";
        public static final String ASSETS_PUBLIC = "/assets/public/**";
        public static final String SERVICES_PUBLIC = "/services/public/**";
    }

}