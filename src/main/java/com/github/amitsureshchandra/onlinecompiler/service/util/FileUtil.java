package com.github.amitsureshchandra.onlinecompiler.service.util;

import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtil {

    public static boolean createFile(String filePath, String content) {
        try (var fileWriter = new FileWriter(filePath);
             var bufferedWriter = new BufferedWriter(fileWriter)) {
            bufferedWriter.write(content);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error writing to the file: " + e.getMessage());
            return false;
        }
    }

    public static boolean createFolder(String userFolder) {
        var folder = new File(userFolder);
        if(!folder.exists()) return folder.mkdir();
        return true;
    }

    public static boolean deleteFolder(String folderPath) {
        try {
            var folder = Paths.get(folderPath);
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
