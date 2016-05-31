import os

def test_hive_site_metastore_connectionURL(File):
    hive = File(os.getenv("HIVE_HOME") + "/conf/hive-site.xml")
    assert hive.is_file
    assert hive.contains("javax.jdo.option.ConnectionURL")

def test_hive_site_metastore_ConnectionUserName(File):
    hive = File(os.getenv("HIVE_HOME") + "/conf/hive-site.xml")
    assert hive.is_file
    assert hive.contains("javax.jdo.option.ConnectionUserName")

def test_hive_site_metastore_ConnectionPassword(File):
    hive = File(os.getenv("HIVE_HOME") + "/conf/hive-site.xml")
    assert hive.is_file
    assert hive.contains("javax.jdo.option.ConnectionPassword")
