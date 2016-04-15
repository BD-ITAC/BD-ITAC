module.exports = function(app){
  var controller = {};
  var userDAO = app.dao.user;

  /**
  * Retorna a lista de todos os usuários
  * @author Danilo Ramalho
  * @param req HTTP request
  * @param res HHTP response
  */
  controller.listUsers = function(req, res, next){
    userDAO.listAll(function(err, data) {
      if(err) {
          res.status(500).json(err);
      }else{
        if(data !== null && data.length >= 0) {
          res.json(data);
        }else {
          res.status(404).json({message:'List of Users Empty.'});
        }
      }
    });
 };

  return controller;

};
