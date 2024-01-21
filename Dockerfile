FROM maven:3-ibm-semeru-17-focal as builder
WORKDIR /opt/app

COPY pom.xml ./
COPY ./src ./src
RUN mvn clean install -DskipTests

FROM eclipse-temurin:17-jre-jammy
WORKDIR /opt/app

ENV CORS_ORIGINS=*
ENV MQ_USERNAME=guest
ENV MQ_PASS=guest
ENV MQ_HOST=localhost
ENV MQ_VHOST=/
ENV MQ_PORT=5672

EXPOSE 8080
COPY --from=builder /opt/app/target/*.jar /opt/app/*.jar

ENTRYPOINT ["java", "-jar", "/opt/app/*.jar"]