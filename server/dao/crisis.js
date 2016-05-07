var db = require("./db");

var crisisDAO = null;

// Inicialização do objeto, obrigando passagem de POOL como parametro.
// Retorna um objeto crisisDAO implementado logo em seguida.
module.exports = function (pool) {
    if (pool != null && crisisDAO == null) {
        crisisDAO = new CrisisDAO(pool);
    }
    return crisisDAO;
};


function CrisisDAO(pool) {
    var self = this;
    if (pool != null) {
        this.pool = pool;
    }

  var dao = {};

  /**
  * Realiza registro de uma nova crise
  * @author Danilo Ramalho
  * @param objeto crisis
  */

  dao.save = function(crisis, callback){
    var query = self.pool.query(
          "insert into crisis(descricao, categoria, nome, email, telefone, latitude, longitude) values (?,?,?,?,?,?,?)",
          [
            crisis.descricao,
            crisis.categoria,
            crisis.nome,
            crisis.email,
            crisis.telefone,
            crisis.latitude,
            crisis.longitude
          ], function (err, rows) {
          if(err)
          {
            callback(err, {});
          }
          else
          {
            return callback(null, {status: 'ok'});
          }
        });
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
