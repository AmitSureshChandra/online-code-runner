package com.github.amitsureshchandra.onlinecompiler.service.shell;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class ShellService {

    public String run(String command) throws IOException, InterruptedException {
        // Start the process
        Process process = Runtime.getRuntime().exec(command);

        StringBuilder sb = new StringBuilder();

        // Read the output from the process
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }

        // Read the error output from the process
        BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        while ((line = errorReader.readLine()) != null) {
            System.err.println("Error: " + line);
        }

        // Wait for the process to complete
        int exitCode = process.waitFor();

        System.out.println("Output : ");
        System.out.println(sb);

        System.out.println("exitCode : "+ exitCode);

        return sb.toString();
    }
}
