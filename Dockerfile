FROM openjdk:20-ea-17-slim

COPY . /project

WORKDIR /project

# use Buildkit
RUN --mount=type=cache,target=/root/.m2 ./mvnw install -DskipTests

ENTRYPOINT ["./mvnw", "spring-boot:run"]
