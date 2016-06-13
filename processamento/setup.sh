#!/bin/bash

# Hadoop
rsync -av /usr/local/BD-ITAC/processamento/confs/hadoop/* $HADOOP_HOME/etc/hadoop/
rsync -av /usr/local/BD-ITAC/processamento/setup/hadoop/sbin/* $HADOOP_HOME/sbin/
chown -R hdfs:hdfs $HADOOP_HOME/

# Hive
rsync -av /usr/local/BD-ITAC/processamento/confs/hive/* $HIVE_HOME/conf/
rsync -av /usr/local/BD-ITAC/processamento/setup/hive/sbin/* $HIVE_HOME/sbin/
chown -R hdfs:hdfs $HIVE_HOME/

# Hue
rsync -av /usr/local/BD-ITAC/processamento/confs/hue/* $HUE_HOME/desktop/conf/
rsync -av /usr/local/BD-ITAC/processamento/setup/hue/sbin/* $HUE_HOME/build/env/sbin/
chown -R hdfs:hdfs $HUE_HOME/

# Setup dir
cp /usr/local/BD-ITAC/processamento/setup/* /setup/

# Bashrc
cp /usr/local/BD-ITAC/processamento/bashrc $HADOOP_HOME/.bashrc
cp /usr/local/BD-ITAC/processamento/bashrc ~/.bashrc

source ~/.bashrc
