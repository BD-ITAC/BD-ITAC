FROM java:8
MAINTAINER BD-ITAC/Jose Marcos Gomes "jose.marcos.gomes@gmail.com"
LABEL description="Mock de Alertas para o sistema BD-ITAC"
VOLUME /tmp
ADD MockAlert-0.0.1.jar MockAlert.jar
RUN bash -c 'touch /MockAlert.jar'
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/MockAlert.jar"]
