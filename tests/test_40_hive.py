import os

HH = os.getenv("HIVE_HOME")

def test_hdfs_mkdir(Command):
    assert Command("%s/bin/hive -e 'show tables;'", HH).rc == 0
