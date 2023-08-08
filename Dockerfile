
FROM eclipse-temurin:17

LABEL mentor="ilemonamustapha@gmail.com"

WORKDIR /app

COPY target/ProphiusLimitedAssessment-0.0.1-SNAPSHOT.jar /app/prophius-limited-assessment.jar

ENTRYPOINT ["java", "-jar", "prophius-limited-assessment.jar"]