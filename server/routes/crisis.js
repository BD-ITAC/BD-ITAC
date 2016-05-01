module.exports = function(app ){
  var uri = '/rest/crisis';
  var controller = app.controllers.crisis;
  app.post(uri+'/', controller.saveCrisis);
  app.post(uri+'/nearbycrisis', controller.saveNearbyCrisis);  
  app.get(uri+'/indicators', controller.listIndicators);
};
