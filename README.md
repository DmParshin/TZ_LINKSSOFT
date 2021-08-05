# CAPTCHA API SERVICE

- Язык разработки: Java 11
- Фреймворк: Spring Boot
- Сборка проекта: Maven
- Фреймворк тестирования: JUnit

Пример использования:
- **localhost:8080/get-captcha** - Получить каптчу    
Возвращает в теле ответа Captcha-картинку в формате PNG, в заголовках (headers) - идентификатор запроса (request_id) и разгадку (captcha_string).*
- **localhost:8080/post-captcha** -  Проверить каптчу  
Тело запроса - JSON с двумя полями: идентификатор запроса (request_id) и разгадка (captcha_string).
Тело ответа - строка "success", если валидация пройдена, и "error" при ошибке валидации, или при любой другой ошибке.

Приложение на heroku.com
- https://captcha-api-service.herokuapp.com/get-captcha
