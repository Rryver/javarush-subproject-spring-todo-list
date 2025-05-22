# Проект по теме: Spring (1)

---

### Задача
Сделать приложение на SpringMVC без использования Spring Boot. 

### Необходимый функционал: 
Список задач (todo-list) с возможностью: 
- просматривать список задач (+ пагинация)
- добавлять новые задачи
- редактировать задачи
- удалять существующие задачи.

### Опциональное задание:
- Упаковать приложение в docker контейнер, добавить docker-compose файл, в котором настроить работу связки приложение-БД в docker-контейнерах.


## Запуск приложения

---

### 1. Через docker-compose

1. Убедиться, что свободны следующие порты:
- `8181` - приложение
- `5050` - pgAdmin
- `5432` - PostgreSQL

2. Выполнить команду в консоли `docker-compose up`
3. Открыть приложение в браузере по адресу <a href="http://localhost:8181">http://localhost:8181</a>


Примечания:
- При изменении кода необходимо пересобрать приложение командой `docker-compose build`
- При изменении файла `db.properties` проверить настройки подключения к postgresql
```properties
dataSource.url=jdbc:postgresql://postgres-container/todo_list
dataSource.schema=public
dataSource.username=postgres
dataSource.password=root
```

---
### 2. Через intelijIdea

1. Запустить PostgreSQL
2. Создать БД с помощью файла `infrastructure/db/dump.sql`
3. Прописать доступы к БД в файле `src/main/java/resources/db.properties`
```properties
dataSource.url=jdbc:postgresql://localhost:5432/todo_list
dataSource.schema=public
dataSource.username=postgres
dataSource.password=root
```

4. Запустить приложение через точку входа `com.kolosov.App -> main()`
5. Открыть приложение в браузере по адресу <a href="http://localhost:8181">http://localhost:8181</a>