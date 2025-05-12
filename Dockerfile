# Usar imagen base de OpenJDK
FROM openjdk:17-jdk-slim

# Establecer directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el archivo JAR de la aplicación
COPY target/ejercicio-tecnico-backend-java-api.jar app.jar

# Exponer el puerto en el que corre la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
