#!/bin/bash

wget --quiet http://downloads.lightbend.com/scala/2.11.8/scala-2.11.8.deb
dpkg -i scala-2.11.8.deb
apt-get update
apt-get install -y scala

wget --quiet http://d3kbcqa49mib13.cloudfront.net/spark-1.4.1-bin-hadoop2.6.tgz
tar xzf spark-1.4.1-bin-hadoop2.6.tgz -C /usr/local/
ln -s /usr/local/spark-1.4.1-bin-hadoop2.6 $SPARK_HOME
rm spark-1.4.1-bin-hadoop2.6.tgz

chown -R $HADOOP_USER:$HADOOP_USER $SPARK_HOME/