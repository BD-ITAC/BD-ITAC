import os

HH = os.getenv("HIVE_HOME")

def test_hive_showdatabases(Command):
    assert Command("su -l hdfs -c \"%s/bin/hive -e 'show databases;'\"", HH).rc == 0

def test_hive_showtables(Command):
    assert Command("su -l hdfs -c \"%s/bin/hive -e 'show tables in default'\"", HH).rc == 0
root@master:~/testinfra# cat test_50_spark.py 
import os

HH = os.getenv("SPARK_HOME")

def test_spark_submit_pi(Command):
    assert Command("su -l hdfs -c \"%s/bin/spark-submit --class org.apache.spark.examples.SparkPi --master local[2] %s/lib/spark-examples*.jar 10\"", HH , HH).rc == 0

def test_spark_example_pi(Command):
    assert Command("%s/bin/run-example SparkPi 10", HH).rc == 0
