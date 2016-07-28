import os

def test_hive_site_metastore_connectionURL(File):
    hive = File(os.getenv("HIVE_HOME") + "/conf/hive-site.xml")
    assert hive.is_file
    assert hive.contains("fs.defaultFS")

def test_hive_site_metastore_ConnectionUserName(File):
    hive = File(os.getenv("HIVE_HOME") + "/conf/hive-site.xml")
    assert hive.is_file
    assert hive.contains("hive.aux.jars.path")
