var str = require('string-validator');

module.exports = function(app){
  var business = {};
  var avisosDAO = app.dao.avisos;

  /**
  * Realiza registro de uma nova crise
  * @author Danilo Ramalho
  * @param objeto avisos
  */
  business.save = function(req, callback){
    //valida preenchimento de campos obrigatórios
    var avisos = req.body;
    var ValidEmail = str.isEmail();
    var erros = '';

    if(!avisos.descricao){
      erros +="descricao,";
    }
    if(!avisos.nome){
      erros +="nome,";
    }
    if(!avisos.email){
      erros +="email,";
    }
    if(!avisos.telefone){
      erros +="telefone,";
    }
    if(!avisos.latitude){
      erros +="latitude,";
    }
    if(!avisos.longitude){
      erros +="longitude,";
    }
    if(!avisos.categoria || avisos.categoria < 0){
      erros +="categoria,";
    }
    if(!ValidEmail(avisos.email)){
      erros +="email-inválido,";
    }
    if(erros)
      {
        if(req.get('content-type') != "application/json" ){
          erros = " content-type=" + req.get('content-type') + " "+erros;
        }
        var message = {
            "message" : {
              "id" : 1,
              "type" : "ERROR",
              "status" : "BAD_REQUEST",
              "description" : "Invalid value data fields:" + erros,
              "info" : "Error"
            }
          };
        callback(message, null);
      }else{
        avisosDAO.save(avisos, function(err, result){
            if(err){
              callback({success: false, message: err}, null);
            }else{

              callback(null, result);
            }
        });
      }
  };

/**
  * Pesquisa alertas proximos a localidade do usuario
*/

 business.nearbyAlerts = function(avisos, callback){

    //valida preenchimento de campos obrigatórios
    if(!avisos.latitude || !avisos.longitude || !avisos.raio ){
        callback({success: false, message: 'Invalid value data fields.'});
      }else{
        avisosDAO.nearbyAlerts(avisos, function(err, result){
            if(err){
              callback({success: false, message: err}, null);
            }else{
              callback(null, {success: true, data: result});
            }
        });
      }
  };

  return business;

  return business;
};
