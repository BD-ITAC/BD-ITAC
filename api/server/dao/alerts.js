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

  dao.listAlerts = function(filtro, callback){
      console.log(getAlerts());
      var query =" SELECT id,         \
              ( 6371 * acos( cos( radians(@latitude) ) * cos( radians( lat ) )     \
                  * cos( radians( lng ) - radians(@longitude) ) + sin( radians(@latitude) )  \
                  * sin( radians( lat ) ) ) ) AS distance  \
              FROM markers \
              HAVING distance < @raio ORDER BY distance LIMIT 0 , 20";
    self.pool.query(
       query, [filtro.latitude, filtro.longitude, filtro.latitude, filtro.raio]
       function(err, rows){
      if(err){
        callback(err,{});
      }else{

        callback(null,getAlerts(rows));
      }
    });

  };


  function getAlerts(rows)
  {
     var alerts = [];

     for (var i in rows) {
       var alert =
             {
               descricao: row[i].descricao,
               datahora: row[i].datahora,
               cidade: row[i].cidade,
               latitude: row[i].latitude,
               longitude: row[i].longitude,
               tipodacrise: row[i].tipodacrise
             }

       alerts.push(alert);

     }


     return alerts;
  };


  return dao;
};
