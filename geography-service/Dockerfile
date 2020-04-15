FROM openjdk:12
ARG IMPLEMENTS
ARG REQUIRES
LABEL IMPLEMENTS=$IMPLEMENTS
LABEL REQUIRES=$REQUIRES
VOLUME /tmp
COPY target/geography-service-1.0-SNAPSHOT.jar /geography-service.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/geography-service.jar"]
EXPOSE 6565/tcp
