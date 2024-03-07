# Система управления новостями

## Микросервис micro-gateway, предоставляющий единую точку входа для клиентов (API). Реализован на Spring Cloud Gateway.

Является частью общей системы
- [ News Management System](https://github.com/rusakovich-viktar/news-management-system/tree/develop)

### Основные сущности и назначение:

- Обеспечивает единый API `/api/v1`
- Общий порт `8080`
- Для всех трех микросервисов - новости, комментарии, поиск

### Стек технологий, примененный в micro-gateway

- Использованы Spring Boot 3.2.3, Java 17, Gradle 8.5 и PostgreSQL 15.1; 
- Spring Data JPA, Hibernate validator, Spring Web, liquibase, mapstruct, redis, spring-cloud-openFeign, spring-cloud-gateway, spring-cloud-config-server, lombok
- Для тестирования использованы junit5, Mockito, Wiremock

### Реализация. Общие пояснения к деталям

Микросервис получает конфиги из micro-config-server-cloud сервиса, поэтому необходимо чтобы сервер с конфигами при
старте данного микросервиса работал.

### Используется одна база данных, к каждой таблице которой обращается свой микросервис

![структура](https://github.com/rusakovich-viktar/NMS-resourses/raw/rusakovich-viktar-patch-1/Снимок%20экрана%202024-03-04%20151246.jpg)

### Реализованы и подключены два Spring Boot Starter:


- logger-aspect-starter реализует логирование запрос-ответ в аспектном стиле (для слоя Controlles)
- exception-handler-starter реализует глобальную обработку исключений и интерпретацию их согласно REST.
- Стартеры при сборке загружаются первыми, далее из локального репозитория .m2 как зависимости подключаются к другим микросервисам.


### Документация swagger доступна по пути `/api`

```
Код документирован @JavaDoc, подключен Swagger (OpenAPI 3.0)
```

### Тестирование. Код покрыт на 85-88%.

![news-coverage](https://github.com/rusakovich-viktar/NMS-resourses/raw/rusakovich-viktar-patch-1/gate-coverage.jpg)

 	Написаны интеграционные тесты
    Использован WireMock в тестах для слоя clients (ответ от микросервиса новостей)

    Для тестрирования порты 8081 и 8082 должны быть свободны, чтобы не мешать поднятию контекста.    
    Также для тестирования база данных поднималась локально. Тестирование проводится в @Profile dev.

### Использован Docker, написан DockerFile


Написаны Dockerfile – для каждого spring boot приложения, создан общий docker-compose.yml для поднятия всех сервисов в контейнерах, настроено взаимодействие между ними)

### Реализована поддержка @Profile prod и dev. Конфиги вынесены в Spring Cloud Config:

- В контексте приложения условно будем считать что профиль prod будет применяться для запуска и взаимодействия микосервисов в docker сети.
Для локальной разработки, а также для тестирования будем использовать профиль dev.
- Профили конфигурируются через micro-config-server-cloud, поэтому он должен быть запущен или в контейнере для профиля prod или локально для профиля dev.
- Для смены профиля dev(локально и тесты) и prod(в докере) необходимо в папка проекта в application.yml сменить активный профиль на 
  - **profiles: active: dev** 
    - или 
  - **profiles: active: prod**

# Как запустить приложение

Является частью сервиса по управлению новостями, запускать совместно с прочими микросервисами по инструкции
из [News Management System](https://github.com/rusakovich-viktar/news-management-system/tree/develop)

- Можно запустить одиночно, но так как он лишь перенаправляет запросы по OpenFeign к другим микросервисам - работать одиночно он не будет.
- Необходимы все микросервисы с их зависимостями.
- Порт для запуска `8080`

## ЭНДПОИНТЫ И ИНТЕРФЕЙС

Все эндпоинты выполнены с учетом требований REST, Документация swagger доступна по пути `/api`
запросы и ответы дублируются как с сервисами micro-news, micro-comments, micro-search с некоторыми изменениями
- все запросы направляются на порт `8080`
- у всех адресов будет предикат `/api/v1`
- запросы к сервису поиска будут изменены вместо ~~search/news?text=мин~~ нужно будет обращаться к `news/search?text=мин`, а вместо
  ~~search/comments?text=пер~~ - к `comments/search?text=пер`, с учетом первых двух пунктов.

<details>
 <summary><strong>
 подробнее - раскрывающийся список эндпоинтов. Список ответов приведен в каждом из трех микросервисов подробно
</strong></summary>


#### 1. Новости
- POST `http://localhost:8080/api/v1/news`
- GET `http://localhost:8080/api/v1/news`
- GET `http://localhost:8080/api/v1/news/{newsId}`
- PUT `http://localhost:8080/api/v1/news/{newsId}`
- DELETE `http://localhost:8080/api/v1/news/{newsId}`
- GET `http://localhost:8080/api/v1/news/{newsId}/comments`
#### 2. Комментарии
- POST `http://localhost:8080/api/v1/comments/news/{newsId}`
- GET `http://localhost:8080/api/v1/comments`
- GET `http://localhost:8080/api/v1/comments/{commentsId}`
- PUT `http://localhost:8080/api/v1/comments/{commentsId}`
- DELETE `http://localhost:8080/api/v1/comments/{commentsId}`
#### 3. Поиск
- GET `http://localhost:8080/api/v1/news/search?text={query}`
- GET `http://localhost:8080/api/v1/comments/search?text={query}`
</details>
