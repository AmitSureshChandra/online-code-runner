package com.github.amitsureshchandra.onlinecompiler.service.file;

import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileService {
    public String createFile(String code) throws IOException {
        String userFolder = UUID.randomUUID().toString();
        File folder = new File(userFolder);
        if(!folder.exists()) folder.mkdir();
        FileWriter fileWriter = new FileWriter(System.getProperty("user.dir") + "/" + userFolder + "/Solution.java");
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(code);
        return userFolder;
    }
}
