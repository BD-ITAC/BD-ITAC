FROM java:8
MAINTAINER BD-ITAC/Jose Marcos Gomes "jose.marcos.gomes@gmail.com"
LABEL description="Mock de receptor de sensores "virtuais" para o sistema BD-ITAC"
VOLUME /tmp
ADD mocksensorservice-0.0.2.jar mocksensorservice.jar
RUN bash -c 'touch /mocksensorservice.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/mocksensorservice.jar"]
