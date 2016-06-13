#!/bin/bash

# Make sure only hdfs can run the script
if [[ $EUID -ne 1000 ]]; then
   echo "This script must be run as hdfs" 1>&2
   exit 1
fi

$HUE_HOME/build/env/bin/hue reset_db --noinput
$HUE_HOME/build/env/bin/hue syncdb --noinput
$HUE_HOME/build/env/bin/hue migrate
