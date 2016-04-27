# Serviço MQTT recebimento de sinais de sensores digitais [BD-ITAC](https://sites.google.com/site/interdproj2016/)

Aplicação Mock usando [Spring Integration](http://projects.spring.io/spring-integration/) e [Spring Boot](http://projects.spring.io/spring-boot) para gerar um serviço MQTT que irá simular a emissão de sinais por sensores analógicos/digitais para a internet.

É um serviço muito simples que não persiste dados. Os dados recebidos ficam armazenados em memória até o sistema ser re-iniciado.

Para usar basta compilar e executar o jar do projeto. Após clonar o projeto no GitHub, execute diretamente na linha de comando:

    mvn clean
    mvn install
    java -jar target/MockSensorSimulator-0.0.1.jar

O Spring BOOT irá tentar conectar com um broker MQTT que poderá estar rodando na máquina local - recomendamos utilizar o [Mosquitto](http://mosquitto.org) ou poderá estar na internet (neste projeto utilizamos o tcp://iop.eclipse.org:1883).

Para gerar um simulador podemos executar um POST no endereço [http://localhost:8082/sensor](http://localhost:8082/sensor) com os dados do sensor no corpo da requisição:

    {
        "tipo":7,
        "topicos":[
            "bditac/temperatura",
            "bditac/pressao",
            "bditac/humidade"
        ]
    }

![POST de sensor](./images/POSTSensor.png)

Também temos uma imagem [Docker](https://hub.docker.com/search/?isAutomated=0&isOfficial=0&page=1&pullCount=0&q=bditac&starCount=0) disponível para executar este serviço sem a necessidade instalar, compilar e configurar exceto o próprio [Docker](http://www.docker.com).

    docker run -t -d -p 8082:8082 bditac/mocksensorsimulator:sp2
    
