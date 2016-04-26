FROM java:8
MAINTAINER BD-ITAC/Jose Marcos Gomes "jose.marcos.gomes@gmail.com"
LABEL description="Mock de receptor de sensores "virtuais" para o sistema BD-ITAC"
VOLUME /tmp
ADD MockSensorService-0.0.1.jar MockSensorService.jar
RUN bash -c 'touch /MockSensorService.jar'
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/MockSensorService.jar"]
