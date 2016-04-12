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
     name: "Danilo",
     email: "daniloramalho@ita.edu.br",
     phone: "12345",
     place: "São João do Longuinho",
     type:  8,
     title: "Chuva FORTE",
     note: "Possível alerta de alagamento."
    },

    {
      name: "Edizon",
      email: "edizonbasseto@ita.edu.br",
      phone: "12345",
      place: "São João do Longuinho",
      type:  8,
      title: "Chuva FORTE",
      note: "Possível alerta de alagamento."
    },

    {
      name: "Fernando",
      email: "fernandojmiguel@ita.edu.br",
      phone: "12345",
      place: "São João do Longuinho",
      type:  8,
      title: "Chuva FORTE",
      note: "Possível alerta de alagamento."
    }
  );

  dao.listCrisis = function(callback){
      return callback(null, _crisis);
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


  return dao;
};
