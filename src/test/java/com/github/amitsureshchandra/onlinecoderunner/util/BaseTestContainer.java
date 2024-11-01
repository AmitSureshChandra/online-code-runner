package com.github.amitsureshchandra.onlinecoderunner.util;

import com.redis.testcontainers.RedisContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.utility.DockerImageName;

public class BaseTestContainer {
    protected static RabbitMQContainer rabbit = new RabbitMQContainer(DockerImageName.parse("rabbitmq:3.7.25-management-alpine"));
    protected static RedisContainer redis = new RedisContainer(DockerImageName.parse("redis:6.2.6"));

    {
        redis.start();
        rabbit.start();
    }


}
