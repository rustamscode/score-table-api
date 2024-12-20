# Тестовое задание на Spring: Турнирная таблица чемпионата
**Автор**: [Rustamscode](https://t.me/agagylrustamov)

## Описание проекта
Проект реализует REST API для управления турнирной таблицей чемпионата. 

**Функциональность:**
1. Регистрация результатов матчей.
2. Получение турнирной таблицы на определённую дату.
3. Автоматический расчёт очков, игр и сортировка команд.

**Технологии:**
- Java 21
- Spring
- PostgreSQL
- Liquibase
- Maven
- Lombok
- Swagger
- JUnit
- Mockito

## Структура
/src/main/java/...    --- Код проекта
<br>/src/main/resources/   --- Ресурсы (application.properties, Liquibase, файлы миграций)
<br>/src/test/...           --- Тесты
<br>pom.xml                 --- Зависимости
<br>README.md               --- Документация проекта

## Подготовительные действия
1. Убедитесь, что на вашем компьютере установлены:
   - **Java 17** или новее
   - **PostgreSQL 15** или новее
   - **Maven 3.8+**

2. Создайте базу данных PostgreSQL:
   ```sql
   CREATE DATABASE championship;

3. Укажите параметры подключения к базе данных в файле src/main/resources/application.properties:

## Запуск
1. Можете запускать проект, скрипт сам создаст команды: 
  - 1:Team A
  - 2:Team B
  - 3:Team C
  - 4:Team D
  - 5:Team E
   
2. После запуска перейдите на http://localhost:8080/swagger-ui.html, чтобы ознакомиться с доступными методами









