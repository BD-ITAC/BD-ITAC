FROM ubuntu:latest
MAINTAINER Luiz Coura <luizcoura@gmail.com>

ENV DEBIAN_FRONTEND noninteractive
ENV JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk-amd64
ENV HADOOP_USER hdfs
ENV HADOOP_VERSION "2.7.2"
ENV HADOOP_HOME "/usr/local/hadoop"

# Requirements
RUN apt-get update && apt-get install -y \
  openssh-server \
  openjdk-8-jdk-headless \
  curl \
&& rm -rf /var/lib/apt/lists/*

# Installing hadoop
RUN curl -SL http://mirrors.ukfast.co.uk/sites/ftp.apache.org/hadoop/common/hadoop-$HADOOP_VERSION/hadoop-$HADOOP_VERSION.tar.gz \
  | tar -xzC /usr/local \
  && ln -s /usr/local/hadoop-$HADOOP_VERSION $HADOOP_HOME
ADD etc/*.xml $HADOOP_HOME/etc/hadoop/

# Set up ssh
RUN mkdir $HADOOP_HOME/.ssh \
  && ssh-keygen -t rsa -P '' -f $HADOOP_HOME/.ssh/id_dsa \
  && cat $HADOOP_HOME/.ssh/id_dsa.pub >> $HADOOP_HOME/.ssh/authorized_keys \
  && chmod 0600 $HADOOP_HOME/.ssh/*
ADD ssh/ssh.config $HADOOP_HOME/.ssh/config
ADD ssh/bashrc $HADOOP_HOME/.bashrc
ADD ssh/profile $HADOOP_HOME/.profile

# Create hadoop user
RUN useradd -d $HADOOP_HOME -s /bin/bash -c 'Hadoop User' $HADOOP_USER \
  && chown -R $HADOOP_USER:$HADOOP_USER $HADOOP_HOME/

# Helper scripts
RUN mkdir -p /setup/hadoop
ADD bin/setup-hdfs /setup/hadoop
RUN chmod 755 /setup/hadoop/setup-hdfs
