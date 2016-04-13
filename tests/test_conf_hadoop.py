import os

def test_core_site(File):
    core = File(os.getenv("HADOOP_HOME") + "/etc/hadoop/core-site.xml")
    assert core.contains("fs.defaultFS")
    assert core.is_file
    assert core.mode == 0o644

def test_hdfs_site(File):
    hdfs = File(os.getenv("HADOOP_HOME") + "/etc/hadoop/hdfs-site.xml")
    assert hdfs.contains("dfs.replication")
    assert hdfs.is_file
    assert hdfs.mode == 0o644
