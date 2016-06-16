var cassandra = require('cassandra-driver'); // cassandra driver
var conf = require('../db/casdb.json');          // cassandra connection configuration


module.exports = function(app) {
    return sensorsDAO();
};

sensorsDAO = function() {

  var dao = {};

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
    console.error('There was an error when connecting', err);
  }
});

dao.listSensors = function(callback){
  client.execute('SELECT * FROM bditac.crise_estacao_captura', function (err, result) {
    if (err) {
      client.shutdown();
      callback(err, null);
    }

      //client.shutdown();
      callback(null, getDados(result.rows));
  });
}
function getDados(rows)
{
  var sensors = [];
  for(i in rows){
    var sensor = rows[i];
    sensors.push(sensor);
  }
  return sensors;
}

return dao;
}
