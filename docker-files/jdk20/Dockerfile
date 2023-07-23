# Use a base image with OpenJDK 8
FROM openjdk:20-jdk

# Set the working directory inside the container
WORKDIR /opt/myapp

# compile & run
ENTRYPOINT ["sh", "-c", "javac Solution.java && java Solution < input.txt"]