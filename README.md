# ДЗ по курсу OTUS Архитектура и шаблоны проектирования

## hw08

Реализован двусторонний обмен данными между Игровым сервером и Агентом при помощи Spring Boot, Spring WebSockets.

Модули:

- server - игровой сервер, на котором вычисляется состояние игры и исполняются команды агента
- agent - приложение, на котором игрок запускает свою тактику
- api - общие сущности, необходимые для обмена сервера и агента
- hw - содержит логику команд на основе IoC контейнера, разработанную ранее и дополненную с учётом текущих требований
