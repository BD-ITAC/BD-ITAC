#!/bin/bash

$HADOOP_HOME/etc/hadoop/hadoop-env.sh
$HADOOP_HOME/sbin/start-dfs.sh

$HADOOP_HOME/bin/hdfs dfs -mkdir -p /user/hive/warehouse
$HADOOP_HOME/bin/hdfs dfs -chmod g+w /user/hive/warehouse
$HADOOP_HOME/bin/hdfs dfs -mkdir -p /hive/auxlib
$HADOOP_HOME/bin/hdfs dfs -put $HIVE_HOME/auxlib/* /hive/auxlib
$HADOOP_HOME/bin/hdfs dfs -chown -R root:root /hive/auxlib/

$HADOOP_HOME/sbin/stop-dfs.sh
