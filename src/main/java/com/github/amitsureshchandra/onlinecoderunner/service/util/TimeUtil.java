package com.github.amitsureshchandra.onlinecoderunner.service.util;

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
}
