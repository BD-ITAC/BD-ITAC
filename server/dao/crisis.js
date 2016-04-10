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
               finalizados : "20",
               emcurso : "8"
            });
  
  dao.listAll = function(callback){
    return callback(null, indicators);
  };


  return dao;
};
