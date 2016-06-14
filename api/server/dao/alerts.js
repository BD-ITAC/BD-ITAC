var db = require("../db/transacao");


var alertsDAO = null;

module.exports = function(app) {
    return alertsDAO(app.db.conexao);
};

alertsDAO = function(pool){

  var dao = {};

  var self = this;
  if (pool !== null) {
      this.pool = pool;
  }

  dao.listAlerts = function(callback){
      console.log(getAlerts());
      /*SELECT alt_id, alt_msg, alt_raio, geo_lat, geo_long, SQRT(
    POW(69.1 * (geo_lat - (-9.44340653710605)), 2) +
    POW(69.1 * ((-70.4864968937616) - geo_long) * COS(geo_lat / 57.3), 2)) AS distance
FROM geografica
inner join alerta on geo_cod = geo_id
HAVING distance < 125 ORDER BY distance;*/
      callback(null,getAlerts());
  };


  function getAlerts(callback)
  {
     var alerts = [];

     var alert =
     {
       descricao:"Alagamento na região de São José dos Campos",
       datahora: "2016-06-12T08:00:32",
       cidade: "São José dos Campos",
       latitude: -23.1966,
       longitude: -45.9468,
       tipodacrise: "Alagamentos"
     }

     alerts.push(alert);

     return alerts;
  };


  return dao;
};
