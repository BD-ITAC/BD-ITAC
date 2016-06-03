import os

def test_core_site(File):
    core = File(os.getenv("HADOOP_HOME") + "/etc/hadoop/core-site.xml")
    assert core.is_file
    assert core.contains("fs.defaultFS")

def test_hdfs_site(File):
    hdfs = File(os.getenv("HADOOP_HOME") + "/etc/hadoop/hdfs-site.xml")
    assert hdfs.is_file
    assert hdfs.contains("dfs.replication")
