#!/bin/bash

# SSH/Profile/Bashrc
cp /usr/local/BD-ITAC/processamento/conf/ssh/ssh.config $HADOOP_HOME/.ssh/config
cp /usr/local/BD-ITAC/processamento/conf/bashrc $HADOOP_HOME/.bashrc
cp /usr/local/BD-ITAC/processamento/conf/profile $HADOOP_HOME/.profile
cp /usr/local/BD-ITAC/processamento/conf/bashrc ~/.bashrc
cp /usr/local/BD-ITAC/processamento/conf/profile ~/.profile

source ~/.bashrc

# Hadoop
rsync -av /usr/local/BD-ITAC/processamento/conf/hadoop/* $HADOOP_HOME/etc/hadoop/
rsync -av /usr/local/BD-ITAC/processamento/setup/hadoop/sbin/* $HADOOP_HOME/sbin/

# Hive
rsync -av /usr/local/BD-ITAC/processamento/conf/hive/* $HIVE_HOME/conf/
rsync -av /usr/local/BD-ITAC/processamento/setup/hive/sbin/* $HIVE_HOME/sbin/

# Hue
rsync -av /usr/local/BD-ITAC/processamento/conf/hue/* $HUE_HOME/desktop/conf/
rsync -av /usr/local/BD-ITAC/processamento/setup/hue/sbin/* $HUE_HOME/build/env/sbin/

# Spark 
rsync -av /usr/local/BD-ITAC/processamento/conf/spark/* $SPARK_HOME/conf/

# Oozie 
rsync -av /usr/local/BD-ITAC/processamento/conf/oozie/* $OOZIE_HOME/conf/
rsync -av /usr/local/BD-ITAC/processamento/setup/oozie/sbin/* $OOZIE_HOME/sbin/

chown -R hdfs:hdfs $HADOOP_HOME/ $HIVE_HOME/ $HUE_HOME/ $SPARK_HOME/ $OOZIE_HOME/

# Setup dir
mkdir /setup
cp /usr/local/BD-ITAC/processamento/setup/* /setup/

