#!/bin/bash

wget --quiet http://mirrors.ukfast.co.uk/sites/ftp.apache.org/hive/hive-1.2.1/apache-hive-1.2.1-bin.tar.gz
tar xzf apache-hive-1.2.1-bin.tar.gz -C /usr/local
ln -s /usr/local/apache-hive-1.2.1-bin $HIVE_HOME
rm apache-hive-1.2.1-bin.tar.gz

chown -R $HADOOP_USER:$HADOOP_USER $HIVE_HOME/