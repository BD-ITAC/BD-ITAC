module.exports = function(app){

  var uri = '/rest/categories';
  var controller = app.controllers.category;

  app.get(uri, controller.listAll);

}
