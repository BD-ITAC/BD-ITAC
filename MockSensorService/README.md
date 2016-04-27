# Serviço MQTT de recebimento de sinais de sensores analógicos/digitais [BD-ITAC](https://sites.google.com/site/interdproj2016/)

Aplicação Mock usando [Spring Integration](http://projects.spring.io/spring-integration/) e [Spring Boot](http://projects.spring.io/spring-boot) para gerar um serviço MQTT que irá receber sinais de um broker MQTT na internet.

É um serviço muito simples que não persiste dados. Os dados recebidos não ficam armazenados em memória e nem re-encaminhados para nenhum outro serviço.

Para usar basta compilar e executar o jar do projeto. Após clonar o projeto no GitHub, execute diretamente na linha de comando:

    mvn clean
    mvn install
    java -jar target/MockSensorSimulator-0.0.1.jar

O Spring BOOT irá tentar conectar com um broker MQTT que poderá estar rodando na máquina local - recomendamos utilizar o [Mosquitto](http://mosquitto.org) ou poderá estar na internet (neste projeto utilizamos o tcp://iop.eclipse.org:1883).

A implementação de recebimento está pronta para ser refatorada com um propósito melhor:

```java 
    private IOTFPayload payload;
    
    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        try {
            this.payload = (IOTFPayload)message.getPayload();
        }
        catch(Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public IOTFPayload getPayload() {
        return this.payload;
    }
```

O formato do `payload` é o mesmo adotado nos modelos de aplicativos IoT da IBM no projeto [Bluemix](http://www.ibm.com/cloud-computing/bluemix/internet-of-things/). Dentro desta estrutura incluimos os atributos para a nossa aplicação

```json
    {
        "d":
        {
            "tipo" : NUMBER,
            "lat" : NUMBER,
            "lon" : NUMBER,
            "alt" : NUMBER,
            "val" : NUMBER
        },
        "ts" : NUMBER
    }
```

Depois de obtido da mensagem MQTT, o `payload` poderá receber o tratamento desejado para o sistema.

Também temos uma imagem [Docker](https://hub.docker.com/search/?isAutomated=0&isOfficial=0&page=1&pullCount=0&q=bditac&starCount=0) disponível para executar este serviço sem a necessidade instalar, compilar e configurar exceto o próprio [Docker](http://www.docker.com).

    docker run -t -d bditac/mocksensorservice:sp2
    
