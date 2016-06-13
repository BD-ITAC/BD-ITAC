module.exports = function(app){
  var uri = '/rest/alerts';
  var controller = app.controllers.alerts;
  /**
   * @api {get} /rest/alerts Listar todos alertas confirmados
   * @apiGroup Alertas
   * @apiVersion 1.0.0
   *
   * @apiParam {String} descricao  Descricao do alerta.
   * @apiParam {DateTime} datahora  Data e hora da confirmação do alerta.
   * @apiParam {String} cidade  Nome da cidade do alerta.
   * @apiParam {Numero} latitude  Latitude da localização.
   * @apiParam {Numero} longitude  Longitude da localização.
   * @apiParam {String} tipodacrise O tipo da crise que originou o alerta.
   *
   *
   */

  app.get(uri, controller.listAlerts);


};
