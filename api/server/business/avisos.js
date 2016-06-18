var str = require('string-validator');

module.exports = function(app){
  var business = {};
  var avisosDAO = app.dao.avisos;

  /**
  * Realiza registro de uma nova crise
  * @author Danilo Ramalho
  * @param objeto avisos
  */
  business.save = function(avisos, callback){
    //valida preenchimento de campos obrigatórios
    var ValidEmail = str.isEmail();

    if((!avisos.descricao || !avisos.nome || !avisos.email || !avisos.telefone
      || !avisos.latitude || !avisos.longitude ||
      !avisos.categoria) || !(avisos.categoria >= 0 && avisos.categoria <= 8)
      || !(ValidEmail(avisos.email))){
        var message = {
            "message" : {
              "id" : 1,
              "type" : "ERROR",
              "status" : "BAD_REQUEST",
              "description" : "Invalid value data fields.",
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
