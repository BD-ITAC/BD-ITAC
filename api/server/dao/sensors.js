var cassandra = require('cassandra-driver'); // cassandra driver
var conf = require('../db/casdb.json');          // cassandra connection configuration

// cassandra connection options
var options = {
    contactPoints : [conf.host], // host to connect (any node in the cluster) 
    authProvider  : new cassandra.auth.PlainTextAuthProvider(conf.userid, conf.password),
    keyspace      : conf.keyspace,
    protocolOptions: {
        port: [conf.port]
    }
}

// class casdb
var client = new cassandra.Client(options);

client.connect(function (err) {
  if (err) {
    client.shutdown();
    return console.error('There was an error when connecting', err);
  }
  client.execute('SELECT * FROM bditac.crise_estacao_captura', function (err, result) {
    if (err) {
      client.shutdown();
      return console.error('There was while trying to retrieve data from system.local', err);
    }
    var row = result.rows[0];
    console.log('Obtained row: ', row);
    console.log('Shutting down');
    client.shutdown();
  });
});