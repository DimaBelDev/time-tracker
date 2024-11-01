# Используем базовый образ с JDK
FROM openjdk:21-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем файл jar в контейнер
COPY target/product-service-1.0-SNAPSHOT.jar /app/product-service.jar

# Указываем команду для запуска приложения
ENTRYPOINT ["java", "-jar", "/app/product-service.jar"]