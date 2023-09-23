
FROM eclipse-temurin:17

LABEL mentor="ilemonamustapha@gmail.com"

WORKDIR /app

COPY target/ProphiusLimitedAssessment-0.0.1-SNAPSHOT.jar /app/ProphiusLimitedAssessment-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "ProphiusLimitedAssessment-0.0.1-SNAPSHOT.jar"]