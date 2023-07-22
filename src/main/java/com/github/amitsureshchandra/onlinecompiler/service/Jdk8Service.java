package com.github.amitsureshchandra.onlinecompiler.service;

import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class Jdk8Service {
    String run(String code) throws IOException, InterruptedException {
        String folderPath = "temp-user";

        File folder = new File(folderPath);

        if(!folder.exists()) folder.mkdir();

//        String content = "public class Solution {public static void main(String[] args) {System.out.println(\"Hello World\");}}";

        try (FileWriter fileWriter = new FileWriter(System.getProperty("user.dir") + "/" + folderPath + "/Solution.java");
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            bufferedWriter.write(code);
        } catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
        }

        // creating a container
        String command = "docker run --name my-openjdk-container --memory 100mb --cpu-quota=100000 -v "+ System.getProperty("user.dir") +"/"+ folderPath +":/opt/myapp my-java-app8";

        System.out.println("command : " + command);
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
