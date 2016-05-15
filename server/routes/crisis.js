module.exports = function(app){
  var uri = '/rest/crisis';
  var controller = app.controllers.crisis;
  app.post(uri+'/', controller.saveCrisis);
  app.get(uri+'/nearbycrisis', controller.nearbyCrisis);
  app.get(uri+'/indicators', controller.listIndicators);
  app.get(uri, controller.listCrisis);
};
