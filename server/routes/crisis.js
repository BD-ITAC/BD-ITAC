module.exports = function(app ){
  var uri = '/rest/crisis';
  var controller = app.controllers.crisis;
  app.post(uri+'/', controller.saveCrisis);
};