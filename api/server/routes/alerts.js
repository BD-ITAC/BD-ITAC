module.exports = function(app){
  var uri = '/rest/alerts';
  var controller = app.controllers.alerts;
  /**
   * @api {get} /rest/alerts?latitude=-9.44340653710605&longitude=-70.4864968937616&raio=10&timestamp=2016-06-14%2000:11:05 Listar todos alertas confirmados por região
   * @apiGroup Alertas
   * @apiVersion 1.0.0
   *
   * @apiParam {Number} latitude  Latitude da localização.
   * @apiParam {Number} longitude  Longitude da localização.
   * @apiParam {Number} raio Raio de alcance em Kms.
   * @apiParam {String} timestamp Data e Hora inicial para consulta. Informe '' caso deseje todos. Para filtrar, use o formato yyyy-mm-ddThh:MM:ss
   *
   * @apiSuccess {String} descricao  Descricao do alerta.
   * @apiSuccess {DateTime} datahora  Data e hora da confirmação do alerta.
   * @apiSuccess {String} cidade  Nome da cidade do alerta.
   * @apiSuccess {Numero} latitude  Latitude da localização.
   * @apiSuccess {Numero} longitude  Longitude da localização.
   * @apiSuccess {String} tipodacrise O tipo da crise que originou o alerta.
   * @apiSuccessExample {json} Request-Example:
   *
   *  {"_embedded":
   *   {"alertsList":[
   *		{"descricao":"Alagamento na região de São José dos Campos",
   *		 "datahora":"2016-06-12T08:00:32",
   *		 "cidade":"São José dos Campos",
   *		 "latitude":-23.1966,
   *		 "longitude":-45.9468,
   *		 "tipodacrise":"Alagamentos"}]
   *   }
   *
   *
   */

  app.get(uri, controller.listAlerts);


};
