package com.github.amitsureshchandra.onlinecompiler.service.file;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
public class FileService {
    public String createFile(String code) throws IOException {
        String userFolder = "temp/" + UUID.randomUUID().toString().substring(0, 6);
        File folder = new File(userFolder);
        if(!folder.exists()) folder.mkdir();
        String filePath = System.getProperty("user.dir") + "/" + userFolder + "/Solution.java";
        FileWriter fileWriter = new FileWriter(filePath);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(code);
        return userFolder;
    }
}
