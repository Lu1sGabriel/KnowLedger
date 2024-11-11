package com.knowledger.knowledger.commom;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Util {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private Util() {
    }

    public static String toDateString(LocalDateTime dateTime) {
        if (Objects.isNull(dateTime)) {
            return null;
        }
        return dateTime.format(formatter);
    }

}