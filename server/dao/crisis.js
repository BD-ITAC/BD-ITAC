module.exports = function(app){
  var dao = {};

  /**
  * Realiza registro de uma nova crise
  * @author Danilo Ramalho
  * @param objeto crisis
  */
  dao.save = function(crisis, callback){
    console.log('dao');
    return callback(null, {status: 'ok'});
  };

  return dao;
};
