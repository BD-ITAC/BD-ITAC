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
          "insert into crise set ?", getDados(crisis), function (err, result) {
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

  function getDados(crisis){
    var dados={
              cri_nome: crisis.nome,
              cri_telefone: crisis.telefone,
              cri_email: crisis.email,
              cri_descricao: crisis.descricao,
              cri_categoria: crisis.categoria,
              cri_latitude: crisis.latitude,
              cri_longitude: crisis.longitude,
              cri_dh_inicio: new Date(),
              cri_ativo: true,
              cri_fotografia: crisis.fotografia
            }
    return dados;
  }

  dao.listCrisis = function(callback){
    self.pool.query("select * from crise", function(err, rows){
      if(err){
        callback(err,{});
      }else{
        callback(null, getCrises(rows));
      }
    });
  };

  function getCrises(rows){
    var crises = [];
    for(c in rows){
      var crise={
                id: rows[c].cri_id,
                nome: rows[c].cri_nome,
                telefone: rows[c].cri_telefone,
                email: rows[c].cri_email,
                descricao: rows[c].cri_descricao,
                categoria: rows[c].cri_categoria,
                latitude: rows[c].cri_latitude,
                longitude: rows[c].cri_longitude,
                dataInicial: rows[c].cri_dh_inicio,
                dataFinal: rows[c].cri_dh_fim,
                ativo: rows[c].cri_ativo,
                fotografia:   rows[c].cri_fotografia
              };
              crises.push(crise);

    }

    return crises;
  }


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
  Criação do mock json
  **/

  dao.nearbyAlerts = function(crisis, callback){
     var nearbyAlerts = [];
     var path = require('path');
     var host = path.join(__dirname);

     //converte raio 100 metros para 0.001
     var calcRaio = (crisis.raio*0.001)/100;

     self.pool.query('SELECT * FROM crise where ST_AsText(ST_Intersection(cri_lng_lat, ST_Buffer(POINT(?, ?), ?))) is not null', [crisis.longitude, crisis.latitude, calcRaio], function(err, rows){
    // self.pool.query('SELECT * FROM crisis WHERE latitude = ? AND longitude = ? AND raio = ?', [crisis.latitude, crisis.longitude, crisis.raio], function(err, rows){
     if(err){
       return callback(err,{});
      }else{
    
     for(a in rows){
          nearbyAlerts.push( {
            "_embedded" : {
              "alertaList" : [ {
                "descricaoResumida" : rows[a].descricao,
                "descricaoCompleta" : rows[a].descricao,
                "categoriaAlerta" : rows[a].categoria,
                "origemLatitude" : rows[a].latitude,
                "origemLongitude" : rows[a].longitude,
                "origemRaioKms" : 10.0, //confirmar existencia no MER
                "_links" : {
                  "self" : {
                    "href" : "http://localhost:3000/rest/crisis/nearbycrisis" + "/" + rows[a].id
                   // "href" : url + "/" + rows[a].id
                  }
                }
              }]
              }
            });
        }

        return callback(null, nearbyAlerts);
  }

  })
  };

  return dao;
};
