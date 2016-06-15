def test_ssh_is_installed(Package):
    ssh = Package("openssh-server")
    assert ssh.is_installed

def test_ssh_running_and_enabled(Service):
    ssh = Service("ssh")
    assert ssh.is_running
    assert ssh.is_enabled

def test_hadoophome_environment_variable(File):
    environment = File("/etc/environment")
    assert environment.is_file
    assert environment.contains("HADOOP_HOME")

def test_javahome_environment_variable(File):
    environment = File("/etc/environment")
    assert environment.is_file
    assert environment.contains("JAVA_HOME")

def test_hivehome_environment_variable(File):
    environment = File("/etc/environment")
    assert environment.is_file
    assert environment.contains("HIVE_HOME")


def test_hivehome_environment_variable(File):
    environment = File("/etc/environment")
    assert environment.is_file
    assert environment.contains("SPARK_HOME")
