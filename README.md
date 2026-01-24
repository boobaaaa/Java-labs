# Петрик Илья ИТ-3 ИКМ (Spring Boot REST + JS)

## Функции 
- Просмотр данных из БД
- Добавление данных в БД
- Редактирование данных в БД
- Удаление данных из БД

CRUD реализован для таблиц:
- `authors` (авторы)
- `books` (книги)
- `readers` (читатели)
- `copies` (экземпляры)
- `issues` (выдачи)

## Технологии
- Java 17
- Spring Boot (Web + Data JPA + Validation)
- PostgreSQL
- Flyway миграции (`backend/src/main/resources/db/migration`)
- UI: HTML + Bootstrap 5 + Vanilla JS (fetch)

## Запуск

### 1) Поднять PostgreSQL (Docker)
В корне проекта:
```bash
docker compose up -d
```

### 2) Запустить backend
```bash
cd backend
mvn spring-boot:run
```

### 3) Открыть интерфейс
- UI: `http://localhost:8080/`
- API (пример): `http://localhost:8080/api/books`

## Настройки БД
По умолчанию backend подключается к:
- `jdbc:postgresql://localhost:5432/library_db`
- user: `postgres`
- pass: `postgres`

Настройки можно менять в `backend/src/main/resources/application.yml`.

## XML-документация
Файл документации: `docs/documentation.xml`.
