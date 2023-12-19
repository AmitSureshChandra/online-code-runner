package com.github.amitsureshchandra.onlinecompiler.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class InitTest {
    public static void init() {
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"chmod", "+x", "run.sh"});
            process.waitFor();
            process = Runtime.getRuntime().exec(new String[]{"./run.sh"});
            process.waitFor();
            System.out.println("docker image added");
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void afterEnd() throws IOException {
        Files.delete(Path.of("temp"));
        Process process = Runtime.getRuntime().exec(new String[]{"docker", "rm", "online-compiler-jdk8", "online-compiler-jdk20", "online-compiler-golang12", "online-compiler-python3", "online-compiler-node20", "online-compiler-gcc11"});
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
