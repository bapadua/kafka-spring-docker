FROM adoptopenjdk/openjdk11:latest
WORKDIR application

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw install -DskipTests

COPY target/campaign-0.0.1-SNAPSHOT.jar japp.jar
CMD ["java", "-jar", "japp.jar"]
