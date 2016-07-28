import os

HH = os.getenv("HIVE_HOME")

def test_hive_showdatabases(Command):
    assert Command("su -l hdfs -c \"%s/bin/hive -e 'show databases;'\"", HH).rc == 0

def test_hive_showtables(Command):
    assert Command("su -l hdfs -c \"%s/bin/hive -e 'show tables in default'\"", HH).rc == 0
