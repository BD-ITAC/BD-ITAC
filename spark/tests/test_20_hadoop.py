import os

HH = os.getenv("HADOOP_HOME")

def test_hdfs_listening(Socket):
    assert Socket("tcp://0.0.0.0:9000").is_listening
    assert Socket("tcp://0.0.0.0:50010").is_listening
    assert Socket("tcp://0.0.0.0:50020").is_listening
    assert Socket("tcp://0.0.0.0:50070").is_listening
    assert Socket("tcp://0.0.0.0:50075").is_listening
    assert Socket("tcp://0.0.0.0:50090").is_listening

def test_yarn_listening(Socket):
    assert Socket("tcp://:::8030").is_listening
    assert Socket("tcp://:::8031").is_listening
    assert Socket("tcp://:::8032").is_listening
    assert Socket("tcp://:::8033").is_listening
    assert Socket("tcp://:::8040").is_listening
    assert Socket("tcp://:::8042").is_listening
    assert Socket("tcp://:::8088").is_listening

def test_hdfs_mkdir(Command):
    assert Command("%s/bin/hdfs dfs -mkdir /testinfra", HH).rc == 0
    assert Command("%s/bin/hdfs dfs -test -d /testinfra", HH).rc == 0

def test_job_teragen_execution(Command):
    assert Command("%s/bin/hadoop jar %s/share/hadoop/mapreduce/hadoop-mapreduce-examples-2.7.2.jar teragen 10000 /testinfra/terasort-input", HH, HH).rc == 0

def test_job_terasort_execution(Command):
    assert Command("%s/bin/hadoop jar %s/share/hadoop/mapreduce/hadoop-mapreduce-examples-2.7.2.jar terasort /testinfra/terasort-input /testinfra/terasort-output", HH, HH).rc == 0

def test_job_teravalidate_execution(Command):
    assert Command("%s/bin/hadoop jar %s/share/hadoop/mapreduce/hadoop-mapreduce-examples-2.7.2.jar teravalidate /testinfra/terasort-output /testinfra/teravalidate-output", HH, HH).rc == 0

def test_hdfs_rmdir(Command):
    assert Command("%s/bin/hdfs dfs -rm -R /testinfra", HH).rc == 0
    assert Command("%s/bin/hdfs dfs -test -d /testinfra", HH).rc == 1
