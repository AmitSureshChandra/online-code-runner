package com.github.amitsureshchandra.onlinecoderunner.service.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
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
        try {
            log.info("Creating directory: " + Paths.get(userFolder).toAbsolutePath());
            Files.createDirectory(Paths.get(userFolder));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteFolder(String folderPath) {
        try {
            FileUtils.deleteDirectory(new File(folderPath));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
