def test_ssh_is_installed(Package):
    ssh = Package("openssh-server")
    assert ssh.is_installed

def test_ssh_running_and_enabled(Service):
    ssh = Service("ssh")
    assert ssh.is_running
    assert ssh.is_enabled
