FROM openjdk:17
#Crate a directory in the image file system
WORKDIR /app
EXPOSE 8090
#copy packaged jar file from our target directory into the app directory in the image.
COPY target/socialmedia.jar /app/socialmedia.jar
ENTRYPOINT ["java", "-jar", "socialmedia.jar"]

#LABEL mentor="ilemonamustapha@gmail.com"

