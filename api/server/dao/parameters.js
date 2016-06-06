var db = require("../db/transacao");


var paramDAO = null;

// Inicialização do objeto, obrigando passagem de POOL como parametro.
// Retorna um objeto paramDAO implementado logo em seguida.
module.exports = function(app) {
    return paramDAO(app.db.conexao);
};


paramDAO = function(pool) {
    var self = this;
    if (pool !== null) {
        this.pool = pool;
    }

  var dao = {};



  dao.saveClassificacao =  function(classif, callback)
  {

    self.pool.query(
          "insert into classificacao set ?", getClassif(classif), function (err, result) {
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
                  "description" : "Classificacao registrada",
                  "info" : "BD-ITAC"
                }
              }

            return callback(null, message);
          }
        });

  };
  function getClassif(cl){
    var dados={
              cla_ds : cl.descricao
            }
    return dados;
  }

  dao.deleteClassificacao = function(key, callback)
  {
    var q = "delete from classificacao where cla_id = " + key
    self.pool.query(
       q,
       function(err){
         if(err){
          callback(err);
        }else{
          callback(null);
        }
      });
  };

  dao.listClassificacao = function(callback){
    self.pool.query(
       "select * from classificacao",
       function(err, rows){
      if(err){
        callback(err,{});
      }else{
        callback(null, getClassificacao(rows));
      }
    });
  };

  function getClassificacao(rows){
    var classif = [];
    for(c in rows){


      var clas={
                id: rows[c].cla_id,
                descricao: rows[c].cla_ds
              };
              classif.push(clas);

    }

    return classif;
  };



 /******** EVENTOS ********/

  dao.saveEvento =  function(evento, callback)
  {
    self.pool.query(
          "insert into evento set ?", getEve(evento), function (err, result) {
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
                  "description" : "Evento registrado",
                  "info" : "BD-ITAC"
                }
              }

            return callback(null, message);
          }
        });

  };
  function getEve(cl){
    var dados={
              eve_ds : cl.descricao,
              eve_cid: cl.cid
            }
    return dados;
  }

  dao.deleteEvento = function(key, callback)
  {
    var q = "delete from evento where eve_id = " + key
    self.pool.query(
       q,
       function(err){
         if(err){
          callback(err);
        }else{
          callback(null);
        }
      });
  };

  dao.listEvento = function(callback){
    self.pool.query(
       "select * from evento",
       function(err, rows){
      if(err){
        callback(err,{});
      }else{
        callback(null, getEvento(rows));
      }
    });
  };

  function getEvento(rows){

    var eventos = [];
    for(c in rows){
      var item={
                id: rows[c].eve_id,
                descricao: rows[c].eve_ds,
                cid: rows[c].eve_cid
              };
              eventos.push(item);
    }
    return eventos;
  };

return dao;

};
