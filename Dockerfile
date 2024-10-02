FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY src src
COPY pom.xml .
COPY stack.env .
COPY deploy.sh .

VOLUME /root/.m2

RUN exec -c ./deploy.sh


FROM openjdk:17-jdk-alpine3.14 AS runtime
COPY --from=build /app/target/*.jar /app/app.jar
CMD ["java", "-jar", "/app/app.jar"]