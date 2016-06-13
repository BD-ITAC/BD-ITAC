#!/bin/bash

# Hadoop
echo "adjusting hadoop..."
rsync -av /usr/local/BD-ITAC/processamento/confs/hadoop/* $HADOOP_HOME/etc/hadoop/
rsync -av /usr/local/BD-ITAC/processamento/setup/hadoop/sbin/* $HADOOP_HOME/sbin/
chown -R hdfs:hdfs $HADOOP_HOME/

# Hive
echo "adjusting hive..."
rsync -av /usr/local/BD-ITAC/processamento/confs/hive/* $HIVE_HOME/conf/
rsync -av /usr/local/BD-ITAC/processamento/setup/hive/sbin/* $HIVE_HOME/sbin/
chown -R hdfs:hdfs $HIVE_HOME/

# Hue
echo "adjusting hue..."
rsync -av /usr/local/BD-ITAC/processamento/confs/hue/* $HUE_HOME/desktop/conf/
rsync -av /usr/local/BD-ITAC/processamento/setup/hue/sbin/* $HUE_HOME/build/env/sbin/
chown -R hdfs:hdfs $HUE_HOME/

# Setup dir
echo "adjusting /setup dir..."
cp /usr/local/BD-ITAC/processamento/setup/* /setup/
