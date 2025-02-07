package com.admin.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class OrderIdUtils {

    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");


    public static String createOrderId() {
        String timestamp = LocalDateTime.now().format(formatter);
        Random random = new Random();
        String seq = Integer.toString(random.nextInt(99999));
        return timestamp + seq;
    }
}
