package com.admin.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class OrderIdUtils {

    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyyMMddHHmmss_SSS");


    public static String createOrderId() {
        String timestamp = LocalDateTime.now().format(formatter);
        String seq = Integer.toString(new Random().nextInt(9000) + 1000);
        return timestamp + seq;
    }
}
