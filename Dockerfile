
FROM openjdk:17

#LABEL mentor="ilemonamustapha@gmail.com"

#WORKDIR /app
EXPOSE 8080

ADD target/socialmedia.jar /socialmedia.jar

ENTRYPOINT ["java", "-jar", "/socialmedia.jar"]
