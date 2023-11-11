FROM openjdk:17
WORKDIR /app
EXPOSE 8090
COPY target/socialmedia.jar /app/socialmedia.jar
ENTRYPOINT ["java", "-jar", "socialmedia.jar"]


