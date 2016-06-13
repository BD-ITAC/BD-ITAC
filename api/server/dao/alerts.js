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
