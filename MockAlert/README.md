# Serviço web de Alertas para o [BD-ITAC](https://sites.google.com/site/interdproj2016/)

Aplicação Mock usando [Spring HATEOAS ReST API](http://projects.spring.io/spring-hateoas) e [Spring Boot](http://projects.spring.io/spring-boot) para gerar um serviço web para registrar eventos e emitir alertas.

É um serviço muito simples que não persiste dados. Os dados recebidos ficam armazenados em memória até o sistema ser re-iniciado.

A documentação provisória pode ser vista [aqui:](https://cdn.rawgit.com/BD-ITAC/BD-ITAC/commit/93690b804adf8d3ee9544d82085b60a2554c2841)

Para usar basta compilar e executar o jar do projeto. Após clonar o projeto no GitHub, no onde o gravou execute diretamente na linha de comando:

    mvn clean
    mvn install
    java -jar target/MockAlert-0.0.1.jar

O Spring BOOT irá executar um Tomcat já embutido dentro do jar e no console podemos observar a execução do mesmo.

Agora no seu browser basta apontar para [http://localhost:8080/evento](http://localhost:8080/evento) para ter acesso a um dos serviços.

Existe ainda um exemplo (script shell *tests.sh* na raiz do projeto) com uma série de comandos utilizando **curl** na raiz do projeto que irá exercitar o serviço e validar algumas funcionalidades básicas. Após executar o exemplo podemos observar o resultado de uma consulta de um serviço apontando para [http://localhost:8080/evento/1](http://localhost:8080/evento/1) e obtendo o evento gravado pelo script.

Se por alguma razão for necessário mudar a porta do serviço basta modificar o atributo **port** no arquivo *<diretório do projeto>/src/main/resources/application.yml*:

    server:
        port: 8080
        address: 0.0.0.0

