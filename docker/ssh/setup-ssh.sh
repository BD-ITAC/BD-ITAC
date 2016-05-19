#!/bin/bash

# passphrase-less ssh:
ssh-keygen -t dsa -P '' -f $HADOOP_HOME/.ssh/id_dsa
cat $HADOOP_HOME/.ssh/id_dsa.pub >> $HADOOP_HOME/.ssh/authorized_keys
chmod 0600 $HADOOP_HOME/.ssh/*

chown -R $HADOOP_USER:$HADOOP_USER $HADOOP_HOME/
