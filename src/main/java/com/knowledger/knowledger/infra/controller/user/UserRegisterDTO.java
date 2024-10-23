package com.knowledger.knowledger.infra.controller.user;

import jakarta.validation.constraints.NotBlank;

public class UserRegisterDTO {
        @NotBlank
        private String name;
        @NotBlank
        private String email;
        @NotBlank
        private String password;
        @NotBlank
        private String confirmedPassword;

        public UserRegisterDTO(String name, String email, String password, String confirmedPassword) {
                this.name = name;
                this.email = email;
                this.password = password;
                this.confirmedPassword = confirmedPassword;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        public String getConfirmedPassword() {
                return confirmedPassword;
        }

        public void setConfirmedPassword(String confirmedPassword) {
                this.confirmedPassword = confirmedPassword;
        }
}
