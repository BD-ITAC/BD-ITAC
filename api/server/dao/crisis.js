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

    var dados = getDados(crisis);
    //Modificado por Edizon: utilizando transações por conta dos campos
    // auto incrementado.
    var query =
    "insert into usuario set \n                  "+
    "usu_nm = '" + dados.cri_nome + "',\n       "+
    "usu_email = '" + dados.cri_email + "',\n    "+
    "usu_fone = '" + dados.cri_telefone + "'; \n\n "+

     "select @idUsuario:=usu_id from usuario where usu_id = LAST_INSERT_ID();\n\n" +
     "insert into crise set \
     cri_ds = '" + dados.cri_descricao + "',\n \
     cri_ini = '" + dados.cri_dh_inicio + "',\n \
     cri_atv = '" + dados.cri_ativo + "',\n \
     cri_pic1 =?,\n \
     cri_pic2 =?,\n \
     cri_pic3 =?;\n\n"+

     "select @idCrise:=cri_id from crise where cri_id = LAST_INSERT_ID();\n\n"+

     "insert into geografica set \n\
     geo_long = '"+dados.cri_longitude+"',\n\
     geo_lat = '"+dados.cri_latitude+"';\n\n"+

     "select @idGeo:=geo_id from geografica where geo_id = LAST_INSERT_ID();\n\n"+

     "insert into ocorrencia set \n\
     oco_cri_cod = @idCrise, \n\
     oco_geo_cod = @idGeo, \n\
     oco_usu_cod = @idUsuario, \n\
     oco_eve_cod = '"+dados.cri_categoria + "';";

     //console.log(query);

     db.executarTransacao(
        self.pool,

        function(conn)
        {
            conn.query(query, [dados.cri_pic1, dados.cri_pic2, dados.cri_pic3]
              ,function (err, result) {
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


              }

            );

        });




/*
    self.pool.query(
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
        */
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
              cri_pic1: new Buffer(crisis.fotografia, 'base64'),
              cri_pic2: '',
              cri_pic3: ''
            }
    return dados;
  }

  dao.listCrisis = function(callback){
    var query =
    "select cri.cri_id  as cri_id,             \
             usu.usu_nm as cri_nome,       \
            usu.usu_fone as cri_telefone,  \
            usu.usu_email as cri_email,    \
            cri.cri_ds as cri_descricao,   \
            event.eve_ds as cri_categoria, \
            geo.geo_lat as cri_latitude,   \
            geo.geo_long as cri_longitude, \
            cri.cri_ini as cri_dh_inicio,  \
            cri.cri_fim as cri_dh_fim,      \
            cri.cri_atv as cri_ativo,      \
            cri.cri_pic1,                  \
            cri.cri_pic2,                  \
            cri.cri_pic3                   \
       from  crise cri                     \
        join ocorrencia ocor on ocor.oco_cri_cod = cri.cri_id  \
        join usuario usu on usu.usu_id = oco_usu_cod           \
        join geografica geo on geo.geo_id = oco_geo_cod        \
        join evento event on event.eve_id = ocor.oco_eve_cod";


    self.pool.query(
       query,
       function(err, rows){
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

      var pic1 = '';
      var pic2 = '';
      var pic3 = '';

      if(rows[c].cri_pic1 != null) pic1 = new Buffer(rows[c].cri_pic1).toString('base64');
      if(rows[c].cri_pic2 != null) pic2 = new Buffer(rows[c].cri_pic2).toString('base64');
      if(rows[c].cri_pic3 != null) pic3 = new Buffer(rows[c].cri_pic3).toString('base64');

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
                pic1:   pic1,
                pic2:   pic2,
                pic3:   pic3
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

});
  };

  dao.listType = function(callback){
    self.pool.query('SELECT * FROM crise_tipo', function(err, rows){
      if(err){
        return callback(err, null);
      }else{
        return callback(null, rows);
      }
    });
  };

  dao.listTypeClass = function(callback){
    self.pool.query('SELECT * FROM crise_tipo_classe', function(err, rows){
      if(err){
        return callback(err, null);
      }else{
        return callback(null, rows);
      }
    });
  };

  dao.listTypeGroup = function(ctc_id, callback){
    self.pool.query('SELECT * FROM crise_tipo_grupo WHERE ctc_id = ?',
    [ctc_id], function(err, rows){
      if(err){
        return callback(err, null);
      }else{
        return callback(null, rows);
      }
    });
  };

  dao.listTypeSubGroup = function(param, callback){
    self.pool.query('SELECT * FROM crise_tipo_subgrupo WHERE ctg_id = ? AND ctc_id = ?',
    [param.ctg_id, param.ctc_id], function(err, rows){
      if(err){
        return callback(err, null);
      }else{
        return callback(null, rows);
      }
    });
  };

  dao.listTypeType = function(param, callback){
    self.pool.query('SELECT * FROM crise_tipo_tipo WHERE cts_id= ? AND ctg_id = ? AND ctc_id', [param.cts_id, param.ctg_id, param.ctc_id], function(err, rows){
      if(err){
        return callback(err, null);
      }else{
        return callback(null, rows);
      }
    });
  };
  
  dao.listTypeSubType = function(param, callback){
    self.pool.query('SELECT * FROM crise_tipo_subtipo WHERE ctt_id= ? AND cts_id = ? AND ctg_id = ? AND ctc_id = ?',
    [param.ctt_id, param.cts_id, param.ctg_id, param.ctc_id], function(err, rows){
      if(err){
        return callback(err, null);
      }else{
        return callback(null, rows);
      }
    });
  };


  return dao;
};
