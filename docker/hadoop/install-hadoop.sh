#!/bin/bash

wget --quiet http://mirrors.ukfast.co.uk/sites/ftp.apache.org/hadoop/common/hadoop-2.7.2/hadoop-2.7.2.tar.gz
tar xzf hadoop-2.7.2.tar.gz -C /usr/local/
ln -s /usr/local/hadoop-2.7.2 $HADOOP_HOME
rm hadoop-2.7.2.tar.gz

chown -R $HADOOP_USER:$HADOOP_USER $HADOOP_HOME/