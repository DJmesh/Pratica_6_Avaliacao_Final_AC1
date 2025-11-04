# Dockerfile para Subscription Service
# Multi-stage build para otimizar tamanho da imagem

FROM openjdk:17-jdk-slim as builder
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jre-slim
WORKDIR /subscription-service

# Copia apenas o JAR da etapa anterior
COPY --from=builder /app/target/*.jar app.jar

# Expõe a porta da aplicação
EXPOSE 8080

# Health check (opcional)
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Executa a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]


