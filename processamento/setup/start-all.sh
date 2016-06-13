#!/bin/bash

# Make sure only root can run the script
if [[ $EUID -ne 0 ]]; then
   echo "This script must be run as root" 1>&2
   exit 1
fi

service mysql start
service ssh start

# HDFS+YARN+HistoryServer
su -l $HADOOP_USER -c "$HADOOP_HOME/etc/hadoop/hadoop-env.sh && $HADOOP_HOME/sbin/start-dfs.sh && $HADOOP_HOME/sbin/start-yarn.sh && $HADOOP_HOME/sbin/mr-jobhistory-daemon.sh --config $HADOOP_CONF_DIR start historyserver"

# HiverServer
echo "starting hiveserver2"
start-stop-daemon --start --pidfile /var/run/hiveserver.pid --make-pidfile --chuid $HADOOP_USER --chdir $HIVE_HOME --background --startas $HIVE_HOME/bin/hive -- --service hiveserver2
#su -l $HADOOP_USER -c "$HIVE_HOME/bin/hive --service hiveserver2 > $HIVE_HOME/logs/hiveserver.log 2>&1 &"

# LivyServer
echo "starting livy_server"
start-stop-daemon --start --pidfile /var/run/livyserver.pid --make-pidfile --chuid $HADOOP_USER --chdir $HUE_HOME --background --startas $HUE_HOME/build/env/bin/hue -- livy_server
#su -l $HADOOP_USER -c "$HUE_HOME/build/env/bin/hue livy_server > $HUE_HOME/logs/livyserver.log 2>&1 &"

# Hue
echo "starting hueserver"
start-stop-daemon --start --pidfile /var/run/hueserver.pid --make-pidfile --chuid $HADOOP_USER --chdir $HUE_HOME --background --startas $HUE_HOME/build/env/bin/hue -- runserver master.bditac.com:8888
#su -l $HADOOP_USER -c "$HUE_HOME/build/env/bin/hue runserver master.bditac.com:8888"
