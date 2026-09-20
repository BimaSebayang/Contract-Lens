package com.contractlens.common.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class TimestampUtils {

    public static String getCurrentTimeStamp(){
        return LocalDateTime.now(ZoneId.of("Asia/Jakarta"))
                .format(DateTimeFormatter.ofPattern(
                        "dd MMMM yyyy, HH:mm 'WIB'",
                        new Locale("id", "ID")
                ));
    }
}
