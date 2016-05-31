FROM gethue/hue:latest
MAINTAINER Luiz Coura <luizcoura@gmail.com>

####################################################
## Ajusta variaveis de ambiente
####################################################
ENV DEBIAN_FRONTEND=noninteractive
ENV HADOOP_USER=hdfs
ENV HADOOP_HOME=/usr/local/hadoop
ENV HIVE_HOME=/usr/local/hive
ENV SPARK_HOME=/usr/local/spark
ENV PATH=$PATH:$HADOOP_HOME/bin:$HADOOP_HOME/sbin:$HIVE_HOME/bin:$SPARK_HOME/bin

####################################################
## Pré-requisitos
####################################################
RUN apt-get update
RUN apt-get install -y software-properties-common
RUN apt-get install -y ssh
RUN apt-get install -y vim
RUN apt-get install -y htop
RUN apt-get install -y curl
RUN apt-get install -y rsync 
RUN apt-get install -y net-tools
RUN apt-get install -y python-pip
RUN apt-get install -y libmysql-java

####################################################
## Define uma pasta de instalação
####################################################
RUN mkdir /setup
WORKDIR /setup

####################################################
## Cria o usuário hdfs
####################################################
RUN useradd -d $HADOOP_HOME -s /bin/bash -c 'Hadoop User' $HADOOP_USER

####################################################
## Instala o hadoop
####################################################
ADD ./hadoop/install-hadoop.sh /setup/
RUN chmod +x ./install-hadoop.sh
RUN ./install-hadoop.sh
ADD ./hadoop/*.xml $HADOOP_HOME/etc/hadoop/

####################################################
## Instala o hive
####################################################
ADD ./hive/install-hive.sh /setup/
RUN chmod +x ./install-hive.sh
RUN ./install-hive.sh
ADD ./hive/hive-site.xml $HIVE_HOME/conf/hive-site.xml
RUN mkdir $HIVE_HOME/auxlib/
ADD ./hive/dependencies/*.jar $HIVE_HOME/auxlib/

####################################################
## Instala o spark
####################################################
ADD ./spark/install-spark.sh /setup/
RUN chmod +x ./install-spark.sh
RUN ./install-spark.sh

####################################################
## Configura o ssh
####################################################
ADD profile $HADOOP_HOME/.profile
RUN mkdir $HADOOP_HOME/.ssh
ADD ./ssh/ssh.config $HADOOP_HOME/.ssh/config
ADD ./ssh/setup-ssh.sh /setup/
RUN chmod +x ./setup-ssh.sh
RUN ./setup-ssh.sh

####################################################
## Configura o hdfs
####################################################
ADD ./hadoop/setup-hdfs.sh /setup/
RUN chmod +x ./setup-hdfs.sh

####################################################
## Configura o hive
####################################################
ADD ./hive/setup-hive.sh /setup/
RUN chmod +x ./setup-hive.sh

####################################################
## Configura o hue
####################################################
ADD ./hue/pseudo-distributed.ini /hue/desktop/conf/
ADD ./hue/livy-defaults.conf /hue/apps/spark/java/conf/

#USER $HADOOP_USER
#RUN /bin/bash -c "source $HADOOP_HOME/.profile ; /setup/setup-hdfs.sh ; /setup/setup-hive.sh"

# HDFS
EXPOSE 50010 50020 50070 50075 50090 8020 9000
# MapReduce UI
EXPOSE 19888
# YARN
EXPOSE 8030 8031 8032 8033 8040 8042 8088
# Hive
EXPOSE 9999 10000 10002 50111
# UIs
EXPOSE 8080

# HUE
EXPOSE 8888

# run servers:
#USER root
ADD start-servers.sh /setup/
RUN chmod +x /setup/start-servers.sh
CMD bash