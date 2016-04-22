# Serviço MQTT recebimento de sinais de sensores digitais [BD-ITAC](https://sites.google.com/site/interdproj2016/)

Aplicação Mock usando [Spring Integration](http://projects.spring.io/spring-integration/) e [Spring Boot](http://projects.spring.io/spring-boot) para gerar um serviço MQTT que irá receber os sinais emitidos por sensores via web.

É um serviço muito simples que não persiste dados. Os dados recebidos ficam armazenados em memória até o sistema ser re-iniciado.

Para usar basta compilar e executar o jar do projeto. Após clonar o projeto no GitHub, execute diretamente na linha de comando:

    mvn clean
    mvn install
    java -jar target/MockSensor-0.0.1.jar

O Spring BOOT irá tentar conectar com um broker MQTT que deverá estar rodando na máquina local - recomendamos utilizar o [Mosquitto](http://mosquitto.org).
