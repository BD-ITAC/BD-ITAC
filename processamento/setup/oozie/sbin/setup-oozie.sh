#!/bin/bash

# Make sure only hdfs can run the script
if [[ $EUID -ne 1000 ]]; then
   echo "This script must be run as hdfs" 1>&2
   exit 1
fi

mysql -uroot -e 'drop database oozie;'
mysql -uroot -e 'create database oozie;' 

$OOZIE_HOME/bin/ooziedb.sh create -sqlfile $OOZIE_HOME/bin/oozie.sql -run
