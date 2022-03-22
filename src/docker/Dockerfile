FROM openjdk:17.0.2-slim AS builder

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract

FROM openjdk:17.0.2-slim
COPY --from=builder dependencies/ ./
COPY --from=builder snapshot-dependencies/ ./
COPY --from=builder spring-boot-loader/ ./
COPY --from=builder application/ ./

ENTRYPOINT java $JAVA_OPTS -cp . org.springframework.boot.loader.JarLauncher

