# Cliente para o Serviço web de Alertas do [BD-ITAC](https://sites.google.com/site/interdproj2016/)

Este é um cliente que atualmente se baseia no serviço web provisório para alertas do projeto [BD-ITAC](https://sites.google.com/site/interdproj2016/).

Este cliente deve ser acoplado à uma aplicação interativa e permite acessar os serviços remotos de alertas. Para utilizá-lo basta adicionar o jar deste projeto na sua aplicação. 

A documentação provisória do serviço web sobre o qual este cliente se baseia pode ser vista [aqui:](https://cdn.rawgit.com/BD-ITAC/BD-ITAC/SP1_TS02/MockAlert/README.html)

Para criar o jar do projeto, após clonar o projeto no GitHub, no onde o gravou execute diretamente na linha de comando:

    mvn clean
    mvn install

Depois copie o **jar** que vai estar em *<diretório do projeto>/target/AlertWSClient-0.0.1.jar* para a pasta do seu projeto e onde ele possa ser acessado. Este **jar** deve fazer parte do pacote do seu projeto.

A utilização do cliente é bastante simples. Basta consultar os testes unitários para ver exemplos de uso. Abaixo um exemplo de consulta a um evento:

```java
EventoService eventoService = new EventoService(HOST_URL);
Evento evento = eventoService.getEventoById(1);
```

Para utilizar em seu ambiente de testes, o *MockAlert*  deve estar sendo executado em sua máquina. A variável *HOST_URL* deve apontar para o endereço local (ou remoto se houver um serviço disponível):

```java
private static final String HOST_URL = "http://localhost:8080";
```
