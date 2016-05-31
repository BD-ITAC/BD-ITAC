#! /bin/bash

$HADOOP_HOME/bin/hdfs namenode -format

chmod +x $HADOOP_HOME/etc/hadoop/hadoop-env.sh
$HADOOP_HOME/etc/hadoop/hadoop-env.sh
$HADOOP_HOME/sbin/start-dfs.sh

$HADOOP_HOME/bin/hdfs dfs -mkdir -p /tmp
$HADOOP_HOME/bin/hdfs dfs -mkdir -p /user/root
$HADOOP_HOME/bin/hdfs dfs -chmod g+w /tmp
$HADOOP_HOME/bin/hdfs dfs -chmod g+w /user/root

$HADOOP_HOME/sbin/stop-dfs.sh
