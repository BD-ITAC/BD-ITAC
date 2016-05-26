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

 controller.login = function(req, res, next){
   if(!req.body.hasOwnProperty('email') ||
      !req.body.hasOwnProperty('password')) {
         res.status(404).json({success: false, message: 'Required fields not informed.'});
     }else{
       userDAO.login(req.body, function(err, user){
           if(err) {
               res.status(500).json(err);
           }else{
               if(user){
                 setSession(req,user);
                 res.json({success:true, detail: user});
               }else{
                 res.status(400).json({success: false, message: 'User or password is invalid'});
               }
           }

       });
     }
 };

 controller.validationSession = function(req, res){
  var id = req.params.id;
  var valid = false;
  if(req.session.user){
    if(req.session.user.usu_id == id){
      valid = true;
    }
  }
  return res.status(200).json(valid);
};

controller.logoutUser = function(req, res){
  req.session.destroy(function(err) {
      if (err) {
          console.log(err);
      }
      else {
          res.status(200).json(true);
      }
  });
};

 function setSession(req, user){
   var sess = req.session;
   sess.user = user;
   sess.logado = true;
 }

  return controller;

};
