var db = require("../db/transacao");

var crisisDAO = null;

// Inicialização do objeto, obrigando passagem de POOL como parametro.
// Retorna um objeto crisisDAO implementado logo em seguida.
module.exports = function(app) {
    return crisisDAO(app.db.conexao);
};


crisisDAO = function(pool) {
    var self = this;
    if (pool !== null) {
        this.pool = pool;
    }

  var dao = {};

  /**
  * Realiza registro de uma nova crise
  * @author Danilo Ramalho
  * @param objeto crisis
  */

  dao.save = function(crisis, callback){//callback(null, {status: 'ok'});
    var query = self.pool.query(
          "insert into crisis set ?", crisis, function (err, result) {
          if(err)
          {
            callback(err, {});
            console.log(err);
          }
          else
          {
            var message = {
                "message" : {
                  "id" : result.insertId,
                  "type" : "INFO",
                  "status" : "OK",
                  "description" : "Crise registrada",
                  "info" : "BD-ITAC"
                }
              }

            return callback(null, message);
          }
        });
  };


  dao.listCrisis = function(callback){
    self.pool.query("select * from crisis", function(err, rows){
      if(err){
        callback(err,{});
      }else{
        callback(null, rows);
      }
    });
  };


  var indicators = [];
  indicators.push({
               cadastrados: 30,
               finalizados : 20,
               emcurso : 8
            });

  dao.listAll = function(callback){
    return callback(null, indicators);
  };

 /**
  Criação do mock json //A ser substituido por chamadas no BD
  **/

  dao.nearbyAlerts = function(crisis, callback){
     self.pool.query("select * from crisis", function(err, rows){
     if(err){
       return callback(err,{});
      }else{
       var result = (rows ? rows[0]:{});
       
  var nearbyAlerts = [];
  nearbyAlerts.push({
              "_embedded" : {
                  "alertaList" : [ {
                     descricaoResumida: result.descricao,
                     descricaoCompleta: result.descricao,
                     categoriaAlerta: result.categoria,
                     origemLatitude:  result.latitude,
                     origemLongitude: result.longitude,
                     origemRaioKms: "" ,
                     "_links" : {
                       "self" : {
                          href: "http://localhost:3000/rest/crisis/nearbycrisis/" 
                                }
                              }
                            }]
                          }
            });
  nearbyAlerts.push({
               "_embedded" : {
                 "alertaList" : [ {
                   descricaoResumida: "Alagamento",
                   descricaoCompleta: "Deslizamento na na favela do Paraiso",
                   categoriaAlerta: "0",
                   origemLatitude:  "40.0",
                   origemLongitude: "50.0",
                   origemRaioKms: "1.0",
                   "_links" : {
                           "self" : {
                              href: "http://localhost:3000/rest/crisis/nearbycrisis/16"
                               }
                              }
                            }]
                           }
            });
 
       return callback(null, nearbyAlerts);
  /**
  * Realiza as query de consulta de alertas no BD
  */
//  dao.nearbyAlerts = function(crisis, callback){
//    return callback(null, result);
//  };

  /**
  * Realiza as query de consulta de alertas no BD
  */
  
  }

  })
  };

  return dao;
};
