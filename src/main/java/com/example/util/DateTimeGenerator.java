package com.example.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateTimeGenerator {
    public static LocalDateTime tomorrowNoon(){
        LocalTime noon = LocalTime.NOON;
        LocalDate today = LocalDate.now(ZoneId.of("Europe/Copenhagen"));
        LocalDateTime todayNoon = LocalDateTime.of(today, noon);

        return todayNoon.plusDays(1);
    }
    public static DateTimeFormatter myFormat() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
}
