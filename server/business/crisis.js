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

    if((!crisis.name || !crisis.email || !crisis.phone || !crisis.place || !crisis.title ||
      !crisis.type) || !(crisis.type >= 0 && crisis.type <= 8) || !(ValidEmail(crisis.email))){
        callback({status: 'Error', message: 'Invalid value data fields.'});
      }else{
        crisisDAO.save(crisis, function(err, result){
            if(err){
              callback({status: 'fail', message: err}, null);
            }else{
              callback(null, {data: result});
            }
        });
      }
  };

  return business;
};
