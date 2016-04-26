FROM java:8
MAINTAINER BD-ITAC/Jose Marcos Gomes "jose.marcos.gomes@gmail.com"
LABEL description="Mock de emissor de sensores "virtuais" para o sistema BD-ITAC"
VOLUME /tmp
ADD MockSensorSimulator-0.0.1.jar MockSensorSimulator.jar
RUN bash -c 'touch /MockSensorSimulator.jar'
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/MockSensorSimulator.jar"]
