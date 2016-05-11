# Serviço web de Alertas para o [BD-ITAC](https://sites.google.com/site/interdproj2016/)

Aplicação Mock usando [Spring HATEOAS ReST API](http://projects.spring.io/spring-hateoas) e [Spring Boot](http://projects.spring.io/spring-boot) para gerar um serviço web para registrar eventos e emitir alertas.

É um serviço muito simples que não persiste dados. Os dados recebidos ficam armazenados em memória até o sistema ser re-iniciado.

A documentação provisória pode ser vista [aqui:](https://cdn.rawgit.com/BD-ITAC/BD-ITAC/SP1_TS02/MockAlert/README.html)

Para usar basta compilar e executar o jar do projeto. Após clonar o projeto no GitHub, no onde o gravou execute diretamente na linha de comando:

    mvn clean
    mvn install
    java -jar target/MockAlert-0.0.1.jar

O Spring BOOT irá executar um Tomcat já embutido dentro do jar e no console podemos observar a execução do mesmo.

Agora no seu browser basta apontar para [http://localhost:8080/evento](http://localhost:8080/evento) para ter acesso a um dos serviços.

## Testes e popular a base *in-memory*

Existe ainda um exemplo (script shell *tests.sh* na raiz do projeto) com uma série de comandos utilizando **curl** na raiz do projeto que irá exercitar o serviço e validar algumas funcionalidades básicas. Após executar o exemplo podemos observar o resultado de uma consulta de um serviço apontando para [http://localhost:8080/evento/1](http://localhost:8080/evento/1) e obtendo o evento gravado pelo script.

O script pode ser extendido para inserir mais dados com o intuito de realizar novos testes.

Se por alguma razão for necessário mudar a porta do serviço basta modificar o atributo **port** no arquivo *<diretório do projeto>/src/main/resources/application.yml*:

    server:
        port: 8080
        address: 0.0.0.0

## Docker

O docker funciona. Basta executar o seguinte comando (basta ter o docker instalado):

    docker run --rm -it -p 8080:8080 bditac/mockalert:sp1

Será feito o download do repositório na internet e vai executar o serviço. Para testar basta apontar para o endereço do host (o meu fica em 192.168.99.100:8080).

>Só uma observação. Se você tentar usar o plugin maven do docker:
>
>    mvn docker:build
>    
>Ele irá:
>
>* Copiar os arquivos *src/main/docker/Dockerfile* e *target/MockAlerta-0.0.1.jar* para a pasta *target/docker*;
>* Tentar executar **docker buil**;
>
>Porém irá falhar miseravelmente, aparentemente porque irá tentar executar as tarefas do docker concorrentemente e vai acabar chocando consigo próprio!

### Gerar imagem Docker

Para gerar uma imagem Docker basta executar a seguinte seqüência de comandos na janela do **Docker Quickstart Terminal**:

    docker login
    
>Aqui estamos deduzindo que você já tenha criado uma conta no site [Docker](https://www.docker.com) e também no [Hub Docker](https://hub.docker.com).

    cd <diretório local *git* onde está o projeto>
    cd target

Vamos criar uma pasta para os objetos que serão gravados na imagem Docker:

    mkdir dockeer
    cd docker
    cp ../MockAlert-0.0.1.jar
    cp ../../src/main/docker/Dockerfile
    
Agora podemos gerar a imagem:

    docker build .
    
O comando irá apresentar uma identificação da imagem gerada. Se você perder esse número pode obtêlo novamente com o seguinte comando:

    docker images
    
Verificar a sua imagem criada a alguns segundos e anotar o *IMAGE ID* para usar no próximo comando:

    docker tag <numero da imagem gerada> <seu id no docker>/<seu repositório no docker hub>:devel
    
Agora você pode subir a sua imagem recém criada (observar que ":devel" não vai aqui):

    docker push <seu id no docker>/<seu repositório no docker hub>
    
Pronto! Seus colegas poderão executar a sua versão com o seguinte comando (observar que aqui vai o ":devel"):

    docker run <seu id no docker>/<seu repositório no docker hub>:devel
    
O Docker na máquina vai baixar a imagem e executá-la logo em seguida. Se ele for executar uma segunda vez basta repetir o comando: a imagem não será baixada novamente e o Docker vai usar o repositório local.
    

## Documentação

A documentação é gerada pelo plugin [Spring ReST Docs](http://projects.spring.io/spring-restdocs/) e escrita usando [Asciidoctor](http://asciidoctor.org/docs/user-manual/).

Para melhorar o aspecto de apresentação de código fonte adicionamos o enfatizador de syntaxes [Pygments](http://pygments.org) que por sua vez utiliza (Python 2)[https://www.python.org].

O Sprint ReST Docs utiliza os testes unitários para gerar a documentação. Para gerar basta executar a seqüência de comandos:

    mvn clean package
    
Será executado um "build" do projeto e gerado um pacote, porém o importante é que serão executadas as fases de teste. Na fase de testes é geração de documentos e em seguida, no empacotamento, os documentos gerados são formatados pelo *Asciidoctor*.

A documentação gerada e formatada é gravada na pasta *target/generated-docs*, que **não é salva no controle de versões** e portanto é descartada. No diretório de fontes *src/main/asciidoc/asciidoc* existe um arquivo chamado *README.adoc*. Este arquivo gera o arquivo *README.html*  que vai para a pasta *target/generated-docs*  juntamente com todos os demais arquivos gerados pelo **Spring ReST Docs**.

A função do arquivo *REAMDE.adoc* é "costurar" o conteúdo gerado pelo **Spring ReST Docs** num único documento - o *README.html*. Basicamente neste arquivo inserimos macros para incluir as várias partes:

    include::{snippets}/evento/locations/curl-request.adoc[]
    
Esse trecho de comando para o **Asciidoctor** instrui a inclusão do documento *curl-request.adoc*  que foi gerado pelo **Spring ReST Docs** durante o teste unitário.

Depois o arquivo *README.html* é copiado manualmente para a raiz do projeto para poder ser lido.

### Documentação dos casos de testes

Utilizamos também o plugin *Asciidoclet* do *Asciidoctor* para gerar a documentação dos casos testes seguindo a forma:

     * == Asserção:
     * 
     * Testa a distância entre três pontos obtidos através da interface web do Google Maps:
     * 
     * == Dados:
     * 
     * === Coordenadas
     *  
     * [source,java]
     * --
     * Coords itaCoords = new Coords(-23.2122219,-45.8804741);
     * Coords centerValeCoords = new Coords(-23.209816,-45.8857956);
     * Coords barCoronelCoords = new Coords(-23.1911191,-45.8951512);
     * --
     * 
     * == Execução
     * 
     * - A distância entre os pontos itaCoords e centerValeCoords é calculada e armazenada em distance1.
     * - A distância entre os pontos itaCoords e barCoronelCoords é calculada e armazenada em distance2.
     * 
     * == Resultado esperado: 
     * 
     * A medida distance1 deve ser *menor* que a medida distance2.

Depois executando o seguinte comando podemos gerar na pasta *target/site/testapidocs* um site contendo o Javadoc dos testes e apresentando os dados necessários para documentar o teste implementado.
