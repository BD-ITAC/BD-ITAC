var str = require('string-validator');

module.exports = function(app){
  var business = {};
  var crisisDAO = app.dao.crisis;
  var avisosDAO = app.dao.avisos;

  /**
  * Realiza registro de uma nova crise
  * @author Danilo Ramalho
  * @param objeto Crisis
  */
  business.save = function(crisis, callback){
    //valida preenchimento de campos obrigatórios
    if((!crisis.regiao_coords || !crisis.descricao || !crisis.cidade || !crisis.tipo
      || !crisis.geoid)){
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
  /*cancelamento de crise na tela de listagem de crise*/
  business.cancelCrisis = function(cri_id, callback){
    if(!cri_id){
      callback({success: false, message: 'Informed value ID the crisis.'}, null);
    }else{
      avisosDAO.cancelAviso(cri_id, function(err, result){
        if(err){
          callback({success: false, message: err}, null);
        }else{
          crisisDAO.cancelCrisis(cri_id, function(err, result){
            if(err){
              callback({success: false, message: err}, null);
            }else{
              callback(null, {success: true, message: result});
            }
          });
        }
      });
    }
  };

  return business;
};
