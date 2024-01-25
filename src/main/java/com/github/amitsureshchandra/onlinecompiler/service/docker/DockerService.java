package com.github.amitsureshchandra.onlinecompiler.service.docker;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class DockerService {
    HashMap<String, String> containerMap = new HashMap<>();
    HashMap<String, String> containerMapInfo = new HashMap<>();

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

    public String getDockerCommand(String userFolder, String compiler, String containerName) {
        switch (compiler) {
            case "jdk" -> {
                return "docker run --name " + containerName + " --memory 100mb --cpu-quota=100000 -v " + userFolder + ":/opt/myapp " + containerMap.get(compiler);
            }
            case "golang12" -> {
                return "docker run --name " + containerName + " --memory 150mb --cpu-quota=100000 -v " + userFolder + ":/usr/src/app " + containerMap.get(compiler);
            }
            case "python3" -> {
                return "docker run --name " + containerName + " --memory 100mb --cpu-quota=100000 -v " + userFolder + ":/usr/src/app " + containerMap.get(compiler);
            }
            case "node20" -> {
                return "docker run --name " + containerName + " --memory 200mb --cpu-quota=100000 -v " + userFolder + ":/usr/src/app " + containerMap.get(compiler);
            }
            case "gcc11" -> {
                return "docker run --name " + containerName + " --memory 250mb --cpu-quota=100000 -v " + userFolder + ":/usr/src/app " + containerMap.get(compiler);
            }
        }
        log.error("command not found for compiler " + compiler);
        throw new RuntimeException("Server Error");
    }

    public int getContainerMapSize() {
        return containerMap.size();
    }
}
