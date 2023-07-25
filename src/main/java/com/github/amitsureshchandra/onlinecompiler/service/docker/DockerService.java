package com.github.amitsureshchandra.onlinecompiler.service.docker;

import com.github.amitsureshchandra.onlinecompiler.exception.ServerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
@Slf4j
public class DockerService {
    HashMap<String, String> containerMap = new HashMap<>();
    HashMap<String, String> containerMapInfo = new HashMap<>();

    @PostConstruct
    void init() {
        containerMap.put("jdk8", "online-compiler-jdk8");
        containerMapInfo.put("jdk8", "Java 8");

        containerMap.put("jdk20", "online-compiler-jdk20");
        containerMapInfo.put("jdk20", "Java 20");

        containerMap.put("golang12", "online-compiler-golang12");
        containerMapInfo.put("golang12", "Golang");

        containerMap.put("python3", "online-compiler-python3");
        containerMapInfo.put("python3", "Python 3");

        containerMap.put("javascript", "online-compiler-javascript");
        containerMapInfo.put("javascript", "Javascript");

        containerMap.put("c/c++", "online-compiler-c");
        containerMapInfo.put("c/c++", "C/C++");

    }

    public Map<String, String> supported() {
        return containerMapInfo;
    }

    public String getDockerCommand(String userFolder, String compiler, String containerName) {
        switch (compiler) {
            case "jdk8":
            case "jdk20":
                return "docker run --name "+ containerName +" --memory 100mb --cpu-quota=100000 -v "+ System.getProperty("user.dir") +"/"+ userFolder +":/opt/myapp " + containerMap.get(compiler);
            case "golang12":
                return "docker run --name "+ containerName +" --memory 150mb --cpu-quota=100000 -v "+ System.getProperty("user.dir") +"/"+ userFolder +":/usr/src/app " + containerMap.get(compiler);
            case "python3":
                return "docker run --name "+ containerName +" --memory 100mb --cpu-quota=100000 -v "+ System.getProperty("user.dir") +"/"+ userFolder +":/usr/src/app " + containerMap.get(compiler);
            case "javascript":
                return "docker run --name "+ containerName +" --memory 200mb --cpu-quota=100000 -v "+ System.getProperty("user.dir") +"/"+ userFolder +":/usr/src/app " + containerMap.get(compiler);
            case "c/c++":
                return "docker run --name "+ containerName +" --memory 250mb --cpu-quota=100000 -v "+ System.getProperty("user.dir") +"/"+ userFolder +":/usr/src/app " + containerMap.get(compiler);
        }
        log.error("command not found for compiler " + compiler);
        throw new ServerException("Server Error");
    }
}
