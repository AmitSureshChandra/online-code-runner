package com.github.amitsureshchandra.onlinecompiler.service.util;

import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileUtil {

    public boolean createFile(String filePath, String content) {
        try (FileWriter fileWriter = new FileWriter(filePath);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            bufferedWriter.write(content);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error writing to the file: " + e.getMessage());
            return false;
        }
    }

    public boolean createFolder(String userFolder) {
        File folder = new File(userFolder);
        if(!folder.exists()) return folder.mkdir();
        return true;
    }

    public boolean deleteFolder(String folderPath) {
        try {
            Path folder = Paths.get(folderPath);
            Files.walk(folder)
                    .sorted((p1, p2) -> p2.toString().length() - p1.toString().length()) // Sort in descending order of path length
                    .map(Path::toFile)
                    .forEach(File::delete);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
