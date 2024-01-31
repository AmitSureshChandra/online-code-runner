package com.github.amitsureshchandra.onlinecompiler.service.docker;

import com.github.amitsureshchandra.onlinecompiler.exception.ValidationException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class DockerService {
    HashMap<String, String> containerMap = new HashMap<>();
    HashMap<String, String> containerMapInfo = new HashMap<>();

    @Value("${compiler-tmp-folder}")
    String compilerTmpFolder;

    @Value("${host-temp-folder}")
    String hostTempFolder;

    @PostConstruct
    void init() {
        containerMap.put("jdk", "online-compiler-jdk");
        containerMapInfo.put("jdk", "Java");

        containerMap.put("golang12", "online-compiler-golang12");
        containerMapInfo.put("golang12", "Golang");

        containerMap.put("python3", "online-compiler-python3");
        containerMapInfo.put("python3", "Python 3");

        containerMap.put("node20", "online-compiler-node20");
        containerMapInfo.put("node20", "Node 20");

        containerMap.put("gcc11", "online-compiler-gcc11");
        containerMapInfo.put("gcc11", "C/C++(gcc11)");
    }

    public Map<String, String> supported() {
        return containerMapInfo;
    }

    String getCompilerTmpFolder() {
        return compilerTmpFolder + File.separator;
    }


    public String getDockerCommand(String userFolder, String compiler, String containerName) {
        log.info("userFolder: {}", userFolder);
        if(!containerMap.containsKey(compiler)) {
            log.error("command not found for compiler " + compiler);
            throw new ValidationException("Some error occurred");
        }
        switch (compiler) {
            case "jdk", "golang12", "python3", "node20", "gcc11" -> {
                return "docker run --name " + containerName + " --memory 512mb --cpu-quota=100000 -v " + getCompilerTmpFolder() + userFolder + ":/opt/myapp " + containerMap.get(compiler);
            }
        }
        throw new RuntimeException("Server Error");
    }

    public int getContainerMapSize() {
        return containerMap.size();
    }
}
