
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY .   /app
EXPOSE 8090
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /appd
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "app.jar"]

