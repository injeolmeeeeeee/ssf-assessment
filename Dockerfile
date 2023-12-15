FROM openjdk:21-bookworm AS builder

WORKDIR /src

COPY src src
COPY mvnw .
COPY mvnw.cmd .
COPY .mvn .mvn
COPY pom.xml .

RUN chmod +x mvnw

# compile the Java application
RUN ./mvnw clean package -Dmaven.skip.test=true

FROM openjdk:21-bookworm

WORKDIR /dir

# Copy and rename to app.jar
COPY --from=builder /src/target/eventmanagement-0.0.1-SNAPSHOT app.jar

EXPOSE ${PORT}

ENTRYPOINT SERVER_PORT=${PORT} java -jar ./app.jar