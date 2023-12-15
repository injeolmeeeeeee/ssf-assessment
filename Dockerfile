FROM openjdk:21-bookworm AS builder

WORKDIR /src

COPY mvnw .
COPY mvnw.cmd .
COPY pom.xml .
COPY .mvn .mvn
COPY src src

RUN mvn clean package -Dmaven.test.skip=true

FROM openjdk:21-bookworm

WORKDIR /app

COPY --from=builder /app/target/d18-lecture-0.0.1-SNAPSHOT.jar healthcheck.jar

ENV SPRING_REDIS_HOST=localhost
ENV SPRING_REDIS_PORT=6379
ENV SPRING REDIS_USERNAME=NOT_SET
ENV SPRING REDIS_PASSWORD=NOT_SET

## Run the application
# Define environment variable 
ENV PORT=8080

#Expose the port
EXPOSE ${PORT}

# Run the program
ENTRYPOINT SERVER_PORT=${PORT} java -jar target/day15-0.0.1-SNAPSHOT.jar
