import pytest
import os

@pytest.mark.parametrize("variable", [
        ("HADOOP_HOME"),
        ("JAVA_HOME"),
	("SPARK_HOME"),
	("HIVE_HOME")])
   
def test_variable(File,variable):
	dir = File(os.environ[variable])
	assert os.environ[variable]
	assert dir.is_directory


def test_ssh_is_installed(Package):
    ssh = Package("openssh-server")
    assert ssh.is_installed

def test_ssh_running_and_enabled(Service):
    ssh = Service("ssh")
    assert ssh.is_running
    assert ssh.is_enabled
