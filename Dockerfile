FROM eclipse-temurin:latest
RUN apt-get update  && apt-get install -y maven
COPY ./target/*.jar /app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app.jar"]
