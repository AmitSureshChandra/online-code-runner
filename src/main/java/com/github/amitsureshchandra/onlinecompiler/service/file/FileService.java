package com.github.amitsureshchandra.onlinecompiler.service.file;

import com.github.amitsureshchandra.onlinecompiler.service.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Slf4j
public class FileService {

    final FileUtil fileUtil;

    public FileService(FileUtil fileUtil) {
        this.fileUtil = fileUtil;
    }

    public String createFile(String code, String input) throws IOException {
        String userFolder = "temp/" + UUID.randomUUID().toString().substring(0, 6);
        File folder = new File(userFolder);
        if(!folder.exists()) folder.mkdir();

        String filePath = System.getProperty("user.dir") + "/" + userFolder + "/Solution.java";
        String inputFilePath = System.getProperty("user.dir") + "/" + userFolder + "/input.txt";

        if(!fileUtil.createFile(filePath, code)) {
            log.error("failed to write to file for code");
            throw new RuntimeException("Server Error");
        }

        if(!fileUtil.createFile(inputFilePath, input == null ? "" : input)) {
            log.error("failed to write to file for input");
            throw new RuntimeException("Server Error");
        }

        return userFolder;
    }

    public void deleteFolder(String folderPath) {
        try {
            Path folder = Paths.get(folderPath);
            Files.walk(folder)
                    .sorted((p1, p2) -> p2.toString().length() - p1.toString().length()) // Sort in descending order of path length
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
