def test_hdfs_listening(Socket):
    hdfs = Socket('tcp://127.0.0.1:9000')
    assert hdfs.is_listening
