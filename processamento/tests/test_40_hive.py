import os

HH = os.getenv("HIVE_HOME")

def test_hive_showtables(Command):
    assert Command("%s/bin/hive -e 'show tables;'", HH).rc == 0

def test_hive_count(Command):
    assert Command("%s/bin/hive -e 'SELECT COUNT(*) FROM u_data;'", HH).rc == 0
