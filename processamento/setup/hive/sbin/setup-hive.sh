#!/bin/bash

# Make sure only hdfs can run the script
if [[ $EUID -ne 1000 ]]; then
   echo "This script must be run as hdfs" 1>&2
   exit 1
fi

rm -rf $HIVE_HOME/metastore_db
rm -rf $HIVE_HOME/derby.log
