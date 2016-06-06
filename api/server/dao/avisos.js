var db = require("../db/transacao");


var avisosDAO = null;

// Inicialização do objeto, obrigando passagem de POOL como parametro.
// Retorna um objeto avisoDAO implementado logo em seguida.
module.exports = function(app) {
    return avisosDAO(app.db.conexao);
};


avisosDAO = function(pool) {
    var self = this;
    if (pool !== null) {
        this.pool = pool;
    }

  var dao = {};

  /**
  * Realiza registro de um novo aviso
  * @author Danilo Ramalho
  * @param objeto avisos
  */
  dao.save = function(avisos, callback){//callback(null, {status: 'ok'});

    var dados = getDados(avisos);
    //Modificado por Edizon: utilizando transações por conta dos campos
    // auto incrementado.

    var query =
    "insert into usuario set \n             "+
    "usu_nm = '" + dados.nome + "',\n       "+
    "usu_email = '" + dados.email + "',\n   "+
    "usu_app = 0, \n" +
    "usu_pwd = '', \n" +
    "usu_ultimo_login = NOW(), \n" +
    "usu_fone = '" + dados.telefone + "'; \n\n "+

     "select @idUsuario:=usu_id from usuario where usu_id = LAST_INSERT_ID();\n\n" +

     "insert into aviso set \n \
     usu_cod = @idUsuario, \n  \
     sta_Cod = 1, \n \
     geo_cod = '" + dados.geoCod + "', \n \
     avs_ds = '" + dados.descricao + "',\n \
     avs_latitude = '" + dados.latitude + "',\n \
     avs_longitude = '" + dados.longitude + "',\n \
     cat_cod = '" + dados.categoria + "';\n " +

     "select @idCrise:=avs_id from aviso where avs_id = LAST_INSERT_ID();\n\n" +

     "insert into imagem set \n " +
     "avs_cod = @idCrise, \n" +
     "img_arq = ? ; ";

     db.executarTransacao(
        self.pool,

        function(conn)
        {
            conn.query(query, [dados.pic]
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




  function getDados(avisos){
    var dados={
              nome: avisos.nome,
              telefone: avisos.telefone,
              email: avisos.email,
              descricao: avisos.descricao,
              categoria: avisos.categoria,
              latitude: avisos.latitude,
              longitude: avisos.longitude,
              geoCod: 1,
              dh_inicio: new Date(),
              ativo: true,
              status: 1,
              pic: new Buffer(avisos.fotografia, 'base64')
            }
    return dados;
  }

  dao.listAvisos = function(callback){
    var query =
    "select avs_id,                                    \
        av.avs_ds as avs_ds,                           \
        avs.sta_ds as sta_ds,                          \
        av.avs_latitude as latitude,                   \
        av.avs_longitude as longitude,                 \
        cat.cat_ds as categoria,                       \
        usu.usu_email,                                 \
        usu.usu_fone,                                  \
        usu.usu_nm,                                    \
        img.img_arq                                    \
   from aviso   av                                     \
   join aviso_status avs on av.sta_cod = avs.sta_id    \
   join categoria cat on av.cat_cod = cat.cat_id       \
   join usuario usu on av.usu_cod = usu.usu_id         \
   join imagem  img on img.avs_cod = av.avs_id";


    self.pool.query(
       query,
       function(err, rows){
      if(err){
        callback(err,{});
      }else{
        callback(null, getAvisos(rows));
      }
    });
  };

  function getAvisos(rows){
    var avisos = [];
    for(c in rows){

      var pic1 = '';

      if(rows[c].img_arq != null) pic1 = new Buffer(rows[c].img_arq).toString('base64');

      var aviso={
                id: rows[c].avs_id,
                nome: rows[c].usu_nm,
                telefone: rows[c].usu_fone,
                email: rows[c].usu_email,
                descricao: rows[c].avs_ds,
                categoria: rows[c].categoria,
                latitude: rows[c].latitude,
                longitude: rows[c].longitude,
                //dataInicial: rows[c].cri_dh_inicio,
                //dataFinal: rows[c].cri_dh_fim,
                status: rows[c].sta_ds,
                pic1:   pic1
              };
              avisos.push(aviso);

    }

    return avisos;
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
