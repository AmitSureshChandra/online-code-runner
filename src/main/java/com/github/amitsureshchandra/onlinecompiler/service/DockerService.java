package com.github.amitsureshchandra.onlinecompiler.service;

import org.springframework.stereotype.Service;

@Service
public class DockerService {
    public String getDockerCommand(String userFolder) {
        return "docker run --memory 100mb --cpu-quota=100000 -v "+ System.getProperty("user.dir") +"/"+ userFolder +":/opt/myapp online-compiler-jdk8";
    }
}
