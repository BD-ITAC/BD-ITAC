
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
  client.execute('SELECT * FROM bditac.crise_estacao_captura LIMIT 10', function (err, result) {
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
    var sensor =
    {
        cec_id: i,
        cec_barometrica: rows[i].cec_barometrica,
        cec_barosit: rows[i].cec_barosit,
        cec_datahora: rows[i].cec_datahora,
        cec_nivelagua: rows[i].cec_nivelagua,
        cec_nivelsit: rows[i].cec_nivelsit,
        cec_pluviometro: rows[i].cec_pluviometro,
        cec_pluvsit: rows[i].cec_pluvsit,
        cec_temperatura: rows[i].cec_temperatura,
        cec_tempsit: rows[i].cec_tempsit,
        cec_umidade: rows[i].cec_umidade,
        cec_umidsit: rows[i].cec_umidsit,
    };
    sensors.push(sensor);
  }
  //console.log(sensors);
  return sensors;
}

return dao;
}
