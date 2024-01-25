FROM maven:3-ibm-semeru-17-focal as builder
WORKDIR /opt/app

COPY pom.xml ./
COPY ./src ./src
COPY ./entry.sh ./entry.sh
COPY ./docker-files ./docker-files
RUN mvn clean install -DskipTests

FROM eclipse-temurin:17-jre-jammy
WORKDIR /opt/app

ENV DOCKER_VERSION=25.0.7

EXPOSE 8090
COPY --from=builder /opt/app/target/*.jar /opt/app/*.jar
COPY --from=builder /opt/app/entry.sh /opt/app/entry.sh
COPY --from=builder /opt/app/docker-files /opt/app/docker-files

RUN apt-get update
RUN apt-get install docker.io -y

#RUN curl -sfL -o docker.tgz "https://download.docker.com/linux/static/stable/x86_64/docker-${DOCKER_VERSION}.tgz" && \
#  tar -xzf docker.tgz docker/docker --strip=1 --directory /usr/local/bin && \
#  rm docker.tgz

RUN chmod +x entry.sh
ENTRYPOINT ["./entry.sh"]