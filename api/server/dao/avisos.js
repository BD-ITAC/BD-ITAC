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
//http://stackoverflow.com/questions/13502360/find-closest-10-cities-with-mysql-using-latitude-and-longitude
     "SELECT @geo_id:= A.geo_id FROM (      \n\
       select *, \n\
       ( 3959 * acos( cos( radians('"+dados.latitude +"') ) * \n\
       cos( radians( geo_lat ) ) * \n\
       cos( radians( geo_long ) - \n\
       radians('"+dados.longitude+"') ) + \n\
       sin( radians('"+dados.latitude+"') ) * \n\
       sin( radians( geo_lat )))) as distance \n\
       from geografica \n\
     ) A WHERE distance < '100' ORDER BY distance ASC LIMIT 0, 1; \n\n"+

     "insert into aviso set \n \
     avs_data = NOW(), \n \
     usu_cod = @idUsuario, \n  \
     sta_Cod = 1, \n \
     geo_cod = @geo_id, \n \
     avs_ds = '" + dados.descricao + "',\n \
     avs_latitude = '" + dados.latitude + "',\n \
     avs_longitude = '" + dados.longitude + "',\n \
     avs_ptcoord=Point('"+dados.longitude+"','"+dados.latitude+"'),\n \
     cat_cod = '" + dados.categoria + "';\n\n " +

     "select @idCrise:=avs_id from aviso where avs_id = LAST_INSERT_ID();\n\n" +

     "insert into imagem set \n " +
     "avs_cod = @idCrise, \n" +
     "img_arq = ? ; \n\n";
console.log(query);
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
              geoCod: avisos.geo_id,
              dt: avisos.data,
              ativo: true,
              status: 1,
              pic: new Buffer(avisos.fotografia, 'base64')
            }
    return dados;
  }

  dao.listAvisos = function(filtro, callback){
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
        img.img_arq,                                   \
        av.avs_data as dt, \
        geo.geo_uf as estado, \
        geo.geo_nm as cidade \
   from aviso   av                                     \
   join aviso_status avs on av.sta_cod = avs.sta_id    \
   join categoria cat on av.cat_cod = cat.cat_id       \
   join usuario usu on av.usu_cod = usu.usu_id         \
   join imagem  img on img.avs_cod = av.avs_id         \
   left join geografica geo on av.geo_cod = geo_id";

    if(filtro != "")
    {
      query += " where av.avs_id = " + filtro;
    }

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
                dt: rows[c].dt,
                //dataInicial: rows[c].cri_dh_inicio,
                //dataFinal: rows[c].cri_dh_fim,
                cidade: rows[c].cidade,
                estado: rows[c].estado,
                status: rows[c].sta_ds,
                fotografia:   pic1
              };
              avisos.push(aviso);

    }

    return avisos;
  }


  dao.listIndicators = function(callback){
    var query = "SELECT sta_ds, count(avs_id) as valor   \
                   FROM  bditac.aviso_status  A \
                   join  bditac.aviso B on A.sta_id = B.sta_cod \
                   group by sta_ds"

                     self.pool.query(
                        query,
                        function(err, rows){
                       if(err){
                         callback(err,{});
                       }else{
                         callback(null, getIndicators(rows));
                       }
                     });

                   };

        function getIndicators(rows)
        {
          var inds = [];
          for(c in rows)
          {
            var ind =
            {
              id: c,
              descricao: rows[c].sta_ds,
              valor: rows[c].valor
            };
            inds.push(ind);
          }
          return inds;
        };

 /**
  Criação do mock json
  **/

  dao.nearbyWarnings = function(avisos, callback){
     var nearbyWarnings = [];
     var path = require('path');
     var host = path.join(__dirname);

     //converte raio 100 metros para 0.001
     var calcRaio = (avisos.raio*0.001)/100;

     var query = 'SELECT * \
                     FROM aviso \
                     where ST_AsText(ST_Intersection(avs_ptcoord, ST_Buffer(POINT(?, ?), ?))) is not null';

    if(avisos.timestamp != "''")
    {
      query += " and avs_data >= " + avisos.timestamp + "";
    }

     self.pool.query(query, [avisos.longitude, avisos.latitude, calcRaio], function(err, rows){
    // self.pool.query('SELECT * FROM crisis WHERE latitude = ? AND longitude = ? AND raio = ?', [crisis.latitude, crisis.longitude, crisis.raio], function(err, rows){
     if(err){
       return callback(err,{});
      }else{

     for(a in rows){
          nearbyWarnings.push( {
            "_embedded" : {
              "alertaList" : [ {
                "descricaoResumida" : rows[a].descricao,
                "descricaoCompleta" : rows[a].descricao,
                "categoriaAlerta" : rows[a].categoria,
                "origemLatitude" : rows[a].latitude,
                "origemLongitude" : rows[a].longitude,
                "origemRaioKms" : 10.0, //confirmar existencia no MER
                /*
                "_links" : {
                  "self" : {
                    "href" : "http://localhost:3000/rest/avisos/nearbyWarnings" + "/" + rows[a].id
                   // "href" : url + "/" + rows[a].id
                  }
                }*/
              }]
              }
            });
        }

        return callback(null, nearbyWarnings);
  }

  })
  };

  return dao;
};
