FROM java:8
MAINTAINER BD-ITAC/Jose Marcos Gomes "jose.marcos.gomes@gmail.com"
LABEL description="Mock de receptor de sensores digitais para o sistema BD-ITAC"
VOLUME /tmp
ADD MockSensor-0.0.1.jar MockSensor.jar
RUN bash -c 'touch /MockSensor.jar'
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/MockSensor.jar"]
