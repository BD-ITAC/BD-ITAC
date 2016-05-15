var str = require('string-validator');

module.exports = function(app){
  var business = {};
  var crisisDAO = app.dao.crisis;

  /**
  * Realiza registro de uma nova crise
  * @author Danilo Ramalho
  * @param objeto Crisis
  */
  business.save = function(crisis, callback){
    //valida preenchimento de campos obrigatórios
    var ValidEmail = str.isEmail();

    if((!crisis.descricao || !crisis.nome || !crisis.email || !crisis.telefone
      || !crisis.latitude || !crisis.longitude ||
      !crisis.categoria) || !(crisis.categoria >= 0 && crisis.categoria <= 8)
      || !(ValidEmail(crisis.email))){
        callback({success: false, message: 'Invalid value data fields.', validationError:true});
      }else{
        crisisDAO.save(crisis, function(err, result){
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

 business.nearbyAlerts = function(crisis, callback){

    //valida preenchimento de campos obrigatórios
    if(!crisis.latitude || !crisis.longitude || !crisis.raio ){
        callback({success: false, message: 'Invalid value data fields.'});
      }else{
        crisisDAO.nearbyAlerts(crisis, function(err, result){
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
