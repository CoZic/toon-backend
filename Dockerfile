FROM openjdk:17

ARG JAR_FILE=build/libs/prac_toon-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} /app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
