
FROM eclipse-temurin:17

#LABEL mentor="ilemonamustapha@gmail.com"

#WORKDIR /app

COPY target/socialmedia.jar /socialmedia.jar

ENTRYPOINT ["java", "-jar", "/socialmedia.jar"]
