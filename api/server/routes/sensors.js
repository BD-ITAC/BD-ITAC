module.exports = function(app){
  var uri = '/rest/sensors';
  var controller = app.controllers.sensors;

  app.get(uri, controller.list);

};
