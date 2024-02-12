package com.github.amitsureshchandra.onlinecoderunner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@SpringBootApplication
public class OnlineCompilerApplication {

    public static void main(String[] args) {
//        String date = "2024-02-11T18:29:23.8003099Z";
//        System.out.println(ZonedDateTime.parse(date.replace("Z", "")).getNano());

//        String dateTimeString1 = "2024-02-12T02:43:20.8542888Z";
//
//        String dateTimeStr = dateTimeString1.substring(0, 19);
//        String nanoStr = dateTimeString1.substring(20, 27);
//
//        System.out.println(dateTimeStr);
//        System.out.println(nanoStr);

        SpringApplication.run(OnlineCompilerApplication.class, args);
    }

}