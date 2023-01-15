package org.symptom.util;

import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    public static LocalDate convert(String date, DateTimeFormatter formatter) {
        return StringUtils.hasLength(date) ? LocalDate.parse(date, formatter) : null;
    }
}
