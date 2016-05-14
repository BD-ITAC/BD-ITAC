module.exports = function(app ){
  var uri = '/rest/users';
  var controller = app.controllers.user;
  app.get(uri, controller.listUsers);
  app.post(uri+'/login', controller.login);
  app.get(uri+'/validation/:id', controller.validationSession);
  app.get(uri+'/logout', controller.logoutUser);
/*  app.post(uri+'/', controller.saveUser);
app.post(uri+'/authenticate', controller.authenticate);
  app.delete(uri+'/:id', controller.deleteUser);
  */
};
