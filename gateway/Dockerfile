FROM adoptopenjdk/openjdk11:latest
WORKDIR application

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw clean install -DskipTests

COPY target/gateway-0.0.1-SNAPSHOT.jar japp.jar
CMD ["java", "-jar", "japp.jar"]
