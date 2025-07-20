# T1-homework-spring-boot-starter

## Состав проекта

Мультимодульный Maven-проект:

- `bishop-starter` — основной модуль с бизнес-логикой (очередь команд, аудит, метрики, обработка ошибок)
- `bishop-prototype` — демонстрационное Spring Boot-приложение, использующее стартер

---

### 1. Конфигурация application.properties

```
spring.application.name=T1_homework_spring_boot_starter
spring.kafka.bootstrap-servers=localhost:9092
spring.docker.compose.enabled=false

bishop.audit.mode=kafka
bishop.audit.topic=audit-log

management.endpoints.web.exposure.include=*
management.metrics.enable.all=true

spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.producer.value-serializer=org.apache.kafka.common.serialization.StringSerializer
```

bishop.audit.mode может принимать значения:
- `kafka` — отправка сообщений в кафку
- `console` — вывод сообщений в консоль

`bishop.audit.topic=audit-log` определяет топик кафки

`spring.docker.compose.enabled=false` — отключение автозапуска спрингом докера, я поднимал его вручную через
```
docker-compose up -d
```

### 2. Конфигурация compose.yml

```
services:
  kafka:
    image: bitnami/kafka:3.7
    container_name: kafka
    ports:
      - "9092:9092"
    environment:
      - KAFKA_CFG_NODE_ID=1
      - KAFKA_CFG_PROCESS_ROLES=controller,broker
      - KAFKA_CFG_CONTROLLER_QUORUM_VOTERS=1@kafka:9093
      - KAFKA_CFG_CONTROLLER_LISTENER_NAMES=CONTROLLER
      - KAFKA_CFG_LISTENERS=PLAINTEXT://:9092,CONTROLLER://:9093
      - KAFKA_CFG_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092
      - KAFKA_CFG_LISTENER_SECURITY_PROTOCOL_MAP=CONTROLLER:PLAINTEXT,PLAINTEXT:PLAINTEXT
      - KAFKA_CFG_AUTO_CREATE_TOPICS_ENABLE=true
    networks:
      - bishop-net

networks:
  bishop-net:
```

Используется Kafka с KRaft. Порт 9092.

### 3. Запуск приложения

Осуществляется запуском BishopPrototypeApplication

### 4. Эндпоинты

- POST /prototype/execute
Отправить команду на исполнение:

```
{
  "description": "Проверить Bishop",
  "priority": "CRITICAL",
  "author": "Ripley",
  "time": "2025-07-20T21:00:00Z"
}
```
Команды с CRITICAL исполняются сразу, COMMON — идут в очередь

- GET /prototype/status
Показать количество команд в очереди

### 5. Проверка Kafka

```
docker exec -it kafka kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic audit-log --from-beginning
```

