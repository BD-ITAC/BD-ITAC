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
      var query ="select alt_msg, alt_timestamp, geo_uf, geo_lat, geo_long     \
                ,( 6371 * acos( cos( radians(?) ) * cos( radians( geo_lat ) )   \
                * cos( radians( geo_long ) - radians(?) ) + sin( radians(?) )  \
	               * sin( radians( geo_lat ) ) ) ) AS distance  \
                 from alerta \
                 join geografica on geo_id = geo_cod \
                 where alt_timestamp = ? having distance < ? ";
    self.pool.query(
       query,
       [filtro.latitude, filtro.longitude, filtro.latitude, filtro.timestamp, filtro.raio]
       ,function(err, rows){
      if(err){
        callback(err,{});
      }else{
        if(rows[0] == null){
          callback(null,null);
        }else {
            callback(null,getAlerts(rows));
        }
      }
    });
  };


  function getAlerts(rows)
  {
     var alerts = [];

     for (var i in rows) {
       var alert =
             {
               descricao: row[i].alt_msg,
               datahora: row[i].alt_timestamp,
               cidade: row[i].geo_uf,
               latitude: row[i].latitude,
               longitude: row[i].geo_lat,
               tipodacrise: row[i].geo_long
             }

       alerts.push(alert);

     }


     return alerts;
  };


  return dao;
};
