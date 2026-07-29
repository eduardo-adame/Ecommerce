# Etapa 1: Construccion (Builder)
FROM eclipse-temurin:17-jdk-alpine AS builder
WORKDIR /app

# Copiar el wrapper de Maven y los archivos de configuración
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

# Dar permisos de ejecución al wrapper de Maven y construir el paquete JAR
RUN chmod +x ./mvnw
RUN ./mvnw clean package -DskipTests

# Etapa 2: Imagen final para producción
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copiar solo el archivo JAR generado en la etapa de construcción
COPY --from=builder /app/target/*.jar app.jar

# Exponer el puerto 8080
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]