# Используем базовый образ с JDK
FROM openjdk:21-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем файл jar в контейнер
COPY target/time-tracker-app-1.0-SNAPSHOT.jar /app/time-tracker-app.jar

# Указываем команду для запуска приложения
ENTRYPOINT ["java", "-jar", "/app/time-tracker-app.jar"]