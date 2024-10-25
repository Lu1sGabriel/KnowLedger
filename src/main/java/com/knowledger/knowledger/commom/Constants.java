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

}