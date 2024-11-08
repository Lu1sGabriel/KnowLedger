package com.knowledger.knowledger.commom;

public class Constants {

    public static class UserRole {
        public static final Long ADMIN = 1L;
        public static final Long USER = 2L;
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
        public static final String USER_LOGIN = "/users/login";
        public static final String USER_REGISTER = "/users/register";
    }

}