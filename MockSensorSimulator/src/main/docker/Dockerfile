FROM java:8
MAINTAINER BD-ITAC/Jose Marcos Gomes "jose.marcos.gomes@gmail.com"
LABEL description="Mock de emissor de sensores "virtuais" para o sistema BD-ITAC"
VOLUME /tmp
ADD mocksensorsimulator-0.0.2.jar mocksensorsimulator.jar
RUN bash -c 'touch /mocksensorsimulator.jar'
EXPOSE 8082
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/mocksensorsimulator.jar"]
