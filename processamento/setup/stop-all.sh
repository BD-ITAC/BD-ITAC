#!/bin/bash

# Make sure only root can run the script
if [[ $EUID -ne 0 ]]; then
   echo "This script must be run as root" 1>&2
   exit 1
fi

# HiverServer
echo "stopping hiveserver2"
start-stop-daemon --stop --pidfile /var/run/hiveserver.pid

# Oozie
echo "stopping oozie"
su -l $HADOOP_USER -c "$OOZIE_HOME/bin/oozied.sh stop"

# HDFS+YARN+HistoryServer
su -l $HADOOP_USER -c "$HADOOP_HOME/etc/hadoop/hadoop-env.sh && $HADOOP_HOME/sbin/mr-jobhistory-daemon.sh --config $HADOOP_CONF_DIR stop historyserver && $HADOOP_HOME/sbin/stop-yarn.sh && $HADOOP_HOME/sbin/stop-dfs.sh"

# Hue
echo "stopping hueserver"
killall supervisor
killall hue

# LivyServer
echo "stopping livyserver"
killall java

service ssh stop
service mysql stop
