package com.github.amitsureshchandra.onlinecoderunner.service.util;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class TimeUtil {
    /**
     *
     * @param time nanoseconds
     */
    static public void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * return array of epoch milliseconds & nanoseconds
     * @param dateTime
     * @return
     */
    public static long[] getMillis(String dateTime) {
        String dateTimeStr = dateTime.substring(0, 19); // fetching only date + time - nanoseconds
        String nanoStr = dateTime.substring(20, 27);
        return new long[] { LocalDateTime.parse(dateTimeStr).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(), Integer.parseInt(nanoStr)};
    }
}
