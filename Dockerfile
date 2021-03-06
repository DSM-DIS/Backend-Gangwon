FROM openjdk:11.0.9-slim

COPY ./build/libs/*.jar dis-auth.jar
ENTRYPOINT ["java", "-Xmx100m","-jar","/dis-auth.jar"]