FROM eclipse-temurin:17-jdk AS build
WORKDIR /app
 
COPY .mvn/ .mvn/
COPY mvnw .
COPY pom.xml .
RUN chmod +x mvnw && ./mvnw -B -q dependency:go-offline
 
COPY src ./src
 
RUN ./mvnw -B -q package -DskipTests
 
FROM eclipse-temurin:17-jre
WORKDIR /app
ENV JAVA_TOOL_OPTIONS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"
 
COPY --from=build /app/target/*.jar app.jar
 
EXPOSE 8080
 
CMD ["java", "-jar", "app.jar"]
 