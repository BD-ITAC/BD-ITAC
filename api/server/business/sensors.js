var str = require('string-validator');

module.exports = function(app){
  var business = {};
  var avisosDAO = app.dao.avisos;

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
