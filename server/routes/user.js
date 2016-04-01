module.exports = function(app ){
  var uri = '/rest/users';
  var controller = app.controllers.user;
  app.get(uri, controller.listUsers);
/*  app.post(uri+'/', controller.saveUser);
  app.delete(uri+'/:id', controller.deleteUser);
  app.post(uri+'/authenticate', controller.authenticate);
  app.get(uri+'/validation/:id', controller.validationSession);
  app.get(uri+'/logout', controller.logoutUser);
  */
};
