package com.github.amitsureshchandra.onlinecoderunner.service.docker;

import com.github.amitsureshchandra.onlinecoderunner.dto.cmd.OutputLogDto;
import com.github.dockerjava.api.model.HostConfig;

import java.util.HashMap;
import java.util.Map;

public interface IDockerService {
    HashMap<String, String> containerMap = new HashMap<>();
    HashMap<String, String> containerMapInfo = new HashMap<>();
    HashMap<String, HostConfig> containerConfigMap = new HashMap<>();

    /**
     * creates container
     * @param compiler
     * @param userFolder user folder to map
     * @return
     */
    String createContainer(String compiler, String userFolder);

    /**
     * starts container
     * @param containerId docker container id
     */
    void startContainer(String containerId);

    /**
     * stop container
     * @param containerId docker container id
     */
    void stopContainer(String containerId);

    /**
     * get container logs
     * @param containerId docker container id
     * @return
     */
    OutputLogDto getContainerLogs(String containerId);

    /**
     * remove container
     * @param containerId docker container id
     */
    void removeContainer(String containerId);

    default Map<String, String> getSupportedLanguages() {
        return containerMapInfo;
    }

    /**
     * return container run duration FinishedAt - StartedAt in nanoseconds
     * @param containerId
     * @return nanoseconds
     */
    long getContainerRunDuration(String containerId);
}
