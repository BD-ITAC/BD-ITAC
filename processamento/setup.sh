#!/bin/bash

# Hadoop
rsync -av /usr/local/BD-ITAC/processamento/conf/hadoop/* $HADOOP_HOME/etc/hadoop/
rsync -av /usr/local/BD-ITAC/processamento/setup/hadoop/sbin/* $HADOOP_HOME/sbin/
chown -R hdfs:hdfs $HADOOP_HOME/

# Hive
rsync -av /usr/local/BD-ITAC/processamento/conf/hive/* $HIVE_HOME/conf/
rsync -av /usr/local/BD-ITAC/processamento/setup/hive/sbin/* $HIVE_HOME/sbin/
chown -R hdfs:hdfs $HIVE_HOME/

# Hue
rsync -av /usr/local/BD-ITAC/processamento/conf/hue/* $HUE_HOME/desktop/conf/
rsync -av /usr/local/BD-ITAC/processamento/setup/hue/sbin/* $HUE_HOME/build/env/sbin/
chown -R hdfs:hdfs $HUE_HOME/

# Setup dir
rm /setup/*
cp /usr/local/BD-ITAC/processamento/setup/* /setup/

# Bashrc
cp /usr/local/BD-ITAC/processamento/conf/bashrc $HADOOP_HOME/.bashrc
cp /usr/local/BD-ITAC/processamento/conf/bashrc ~/.bashrc

source ~/.bashrc
