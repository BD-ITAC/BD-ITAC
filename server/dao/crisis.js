module.exports = function(app){
  var dao = {};

  /**
  * Realiza registro de uma nova crise
  * @author Danilo Ramalho
  * @param objeto crisis
  */

  dao.save = function(crisis, callback){    
    return callback(null, {status: 'ok'});
  };

  var indicators = [];
  indicators.push({
               cadastrados: "30",
               finalizados: "20",
               emcurso: "8"
            });
  
  dao.listAll = function(callback){
    return callback(null, indicators);
  };

 /**
  Criação do mock json //A ser substituido por chamadas no BD
  **/
  var nearbyAlerts = [];
  nearbyAlerts.push({
               place: "São José dos Campos",
               type: "5",
               title: "Chuva Forte"
            });
  nearbyAlerts.push({
               place: "São José dos Campos",
               type: "4",
               title: "Barragem"
            });

  /**
  * Realiza as query de consulta de alertas no BD
  */
  dao.alerts = function(crisis, callback){
    return callback(null, nearbyAlerts);
  };

  return dao;
};
