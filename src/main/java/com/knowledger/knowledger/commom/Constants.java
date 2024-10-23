package com.knowledger.knowledger.commom;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Constants {

    public static class UserRole {
        public static final Long ADMIN = 1L;
        public static final Long USER = 2L;
    }

    public static String toDateString(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dateTime.format(formatter);
    }

}
