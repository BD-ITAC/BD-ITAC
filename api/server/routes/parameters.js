/******
* Rota para cadastrar e listar paremtros do aplicativo
* @author: Edizon
*/
module.exports = function(app){
  var uri = '/rest/parameters';
  var controller = app.controllers.parameters;

  app.post(uri+'/classificacao', controller.saveclassificacao);
  app.get (uri+'/classificacao', controller.listClassificacao);
  app.delete(uri+'/classificacao/:id', controller.deleteClassificacao);

  app.post(uri+'/evento', controller.saveEvento);
  app.get (uri+'/evento', controller.listEvento);
  app.delete(uri+'/evento/:id', controller.deleteEvento);

};
