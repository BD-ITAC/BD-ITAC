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

   var _crisis = [];
	_crisis.push(
    {
       name: "Fulano",
       email: "fulano@ita.edu.br",
       phone: "12345",
       place: "São João do Longuinho",
       title: "Chuva FORTE",
       type:  8
    }

  );

  dao.listCrisis = function(callback){
      return callback(null, _crisis);
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
