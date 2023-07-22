package com.github.amitsureshchandra.onlinecompiler.service.docker;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;

@Service
public class DockerService {
    HashMap<String, String> map = new HashMap<>();

    @PostConstruct
    void init() {
        map.put("jdk8", "online-compiler-jdk8");
    }

    public String getDockerCommand(String userFolder, String compiler, String containerName) {
        return "docker run --name "+ containerName +" --memory 100mb --cpu-quota=100000 -v "+ System.getProperty("user.dir") +"/"+ userFolder +":/opt/myapp " + map.get(compiler);
    }
}
