#!/bin/bash

# Make sure only hdfs can run the script
if [[ $EUID -ne 1000 ]]; then
   echo "This script must be run as hdfs" 1>&2
   exit 1
fi

# Format the HDFS
if [ -f /tmp/hadoop-$HADOOP_USER/dfs/data/current/VERSION ]; then
  CLUSTERID=$(grep clusterID /tmp/hadoop-$HADOOP_USER/dfs/data/current/VERSION | awk -F "=" '{printf $2}')
  $HADOOP_HOME/bin/hdfs namenode -format -force -nonInteractive -clusterId $CLUSTERID
else
  $HADOOP_HOME/bin/hdfs namenode -format -force -nonInteractive
fi

$HADOOP_HOME/etc/hadoop/hadoop-env.sh
$HADOOP_HOME/sbin/start-dfs.sh

# Hadoop
$HADOOP_HOME/bin/hdfs dfs -mkdir -p /tmp
$HADOOP_HOME/bin/hdfs dfs -chmod g+w /tmp
$HADOOP_HOME/bin/hdfs dfs -mkdir -p /user/$HADOOP_USER
$HADOOP_HOME/bin/hdfs dfs -chmod g+w /user/$HADOOP_USER
$HADOOP_HOME/bin/hdfs dfs -mkdir -p /mr-history/tmp 
$HADOOP_HOME/bin/hdfs dfs -chmod -R 1777 /mr-history/tmp 
$HADOOP_HOME/bin/hdfs dfs -mkdir -p /mr-history/done 
$HADOOP_HOME/bin/hdfs dfs -chmod -R 1777 /mr-history/done
$HADOOP_HOME/bin/hdfs dfs -mkdir -p /app-logs
$HADOOP_HOME/bin/hdfs dfs -chmod -R 1777 /app-logs

# Hive
$HADOOP_HOME/bin/hdfs dfs -mkdir -p /user/hive/warehouse
$HADOOP_HOME/bin/hdfs dfs -chmod g+w /user/hive/warehouse
$HADOOP_HOME/bin/hdfs dfs -mkdir -p /hive/auxlib
$HADOOP_HOME/bin/hdfs dfs -put $HIVE_HOME/auxlib/* /hive/auxlib
$HADOOP_HOME/bin/hdfs dfs -chown -R root:root /hive/auxlib/

# Spark
$HADOOP_HOME/bin/hdfs dfs -mkdir -p /spark/auxlib
$HADOOP_HOME/bin/hdfs dfs -put $SPARK_HOME/lib/* /spark/auxlib
$HADOOP_HOME/bin/hdfs dfs -chown -R root:root /spark/auxlib/

$HADOOP_HOME/sbin/stop-dfs.sh
