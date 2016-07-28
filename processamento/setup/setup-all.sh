#!/bin/bash

# Make sure only root can run the script
if [[ $EUID -ne 0 ]]; then
   echo "This script must be run as root" 1>&2
   exit 1
fi

echo "THIS OPERATION IS IRREVERSIBLE"
read -r -p "Are you sure? [y/N] " RESPONSE
case $RESPONSE in
  [yY][eE][sS]|[yY]) 

    [ -d "$HADOOP_HOME/logs" ] && find $HADOOP_HOME/logs -type f -exec rm {} \;
    [ -d "$HIVE_HOME/logs" ] && find $HIVE_HOME/logs -type f -exec rm {} \;
    [ -d "$HUE_HOME/logs" ] && find $HUE_HOME/logs -type f -exec rm {} \;

    service mysql start
    service ssh start

    # Prepare HDFS
    su -l $HADOOP_USER -c "$HADOOP_HOME/sbin/setup-hdfs.sh"
    su -l $HADOOP_USER -c "$HIVE_HOME/sbin/setup-hive.sh"
    su -l $HADOOP_USER -c "$HUE_HOME/build/env/sbin/setup-hue.sh"
    su -l $HADOOP_USER -c "$OOZIE_HOME/sbin/setup-oozie.sh"

    service ssh stop
    service mysql stop
  ;;
  *)
    exit 0
  ;;
esac
