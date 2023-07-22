# Use a base image with OpenJDK 8
FROM openjdk:8-jdk

# Set the working directory inside the container
WORKDIR /opt/myapp

# compile
CMD ["javac", "Solution.java"]

# run
CMD ["java", "Solution"]