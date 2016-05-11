# Aplicativo móvel para o [BD-ITAC](https://sites.google.com/site/interdproj2016/)

Aplicação móvel utilizando [Spring for Android](http://projects.spring.io/spring-android/) para executar os comandos HTTP de envio e recebimento de requisições e repostas do serviço de alertas, e que por sua vez utiliza a biblioteca [Jackson](http://wiki.fasterxml.com/JacksonHome) para serializear e desserializar dados no formato JSON (JavaScript Object Notation).

>**Observação sobre o uso do Jackson e templates de renderização**:
>
>*A biblioteca Jackson serve tanto para JSON quanto para XML e vários componentes do Spring Framework fazem uso desse recurso. O porém é que em diversos pontos são utilizadas notações [JAXB](https://jaxb.java.net) (Java Architecture for XML Bindings) que faz parte do runtime do Java desde a versão 1.6.*
>
>*Em decorrência disso o aplicativo pode ser compilado e até testes unitários rodam no ambiente de desenvolvimento, porém ele não pode ser executado no emulador ou num celular porque o runtime do Android não possui a implementação de JAXB.*
>
>As alternativas seriam:
>* _Importar a biblioteca JAXB no projeto e instalá-la juntamente com o aplicativo no celular - seria a opção mais fácil e numa primeira tentativa não funcionou e além do mais isso poderia levar a problemas futuros quando o Android for atualizado para incluir o JAXB;_
>
>* _Implementar JAXB dentro do aplicativo - isso estaria muito além do escopo desse projeto e levaria vários dias para ser implementado além de não dar garantias de que essa implementação não acabaria causando outros problemas;_
>
>* _Rever a implementação tanto do serviço Mock quanto do cliente para utilizarem outras soluções de serialização e desserialização - seria a alternativa mais simples e lógica com o porém de termos de aprender uma nova API de integração com serviços web;_
>
>* _Modificar a implementação dos componentes do Spring Framework que utilizam JAXB para não o implementarem na interface com Jackson - acabamos optando por esta alternativa pois apesar de parecer mais custosa nos deu a oportunidade de conhecer um pouco mais uma ferramenta com a qual já estamos acostumados a trabalhar._

## Estrutura da aplicação e fontes

### alertaApp

Módulo responsável pela interação com o usuário do aplicativo móvel.

Possui as telas:
* Cadastro de eventos - cadastramento de uma ocorrência de evento de crise pelo próprio usuário;
* Indicadores de alertas e crises - painel administrativo que mostra indicadores de alertas e crises;
* Notificação de alertas - notificação apresentada na bandeja da interface principal do Android;
* Configurações - configurações do usuário sobre o funcionamento do aplicativo.

### alertaWSClient

Módulo responsável pela interação com o serviço web de alertas.

Esse módulo hoje integra com o Mock de Alertas desenvolvido para testes. Deve ser substituído por um novo módulo quando o serviço de alertas entrar em sua forma definitiva.

### springAndroidRest

Módulo responsável pela refatoração dos componentes do Spring Framework que integram com JAXB (ver observação acima).