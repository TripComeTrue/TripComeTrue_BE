FROM openjdk:17-jdk-slim
WORKDIR /app
COPY . .
COPY .env .env
ARG JAR_FILE_PATH=build/libs/*.jar
COPY ${JAR_FILE_PATH} app.jar
ENV TZ=Asia/Seoul
ENTRYPOINT ["java", "-jar", "app.jar"]
