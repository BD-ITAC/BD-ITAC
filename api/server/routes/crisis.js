module.exports = function(app){
  var uri = '/rest/crisis';
  var controller = app.controllers.crisis;

  app.post(uri+'/', controller.saveCrisis);
  app.get(uri, controller.listCrisis);
  app.get(uri+'/nearbycrisis', controller.nearbyCrisis);
  app.get(uri+'/indicators', controller.listIndicators);
  /*PALAVRAS CHAVES DAS CRISES*/
  /**
   * @api {get} /rest/crisis/type Palavras Chaves de Crises
   * @apiVersion 1.0.0
   * @apiGroup Crisis
   *
   * @apiSuccess {Numero} código do tipo/palavra da crise.
   * @apiSuccess {Numero} ctp_id código do tipo do sub-tipo da crise.
   * @apiSuccess {Numero} ctt_id código do tipo do tipo da crise.
   * @apiSuccess {Numero} cts_id código do tipo de subgrupo da crise.
   * @apiSuccess {Numero} ctg_id código do tipo de grupo da crise.
   * @apiSuccess {Numero} ctc_id código do tipo da classe da crise.
   * @apiSuccess {String} descrição do tipo da crise.
   * @apiSuccess {String} descrição da definição do tipo da crise.
   * @apiSuccess {String} descrição da definição do tipo da crise.
   *
   * @apiSuccessExample {json} Sucesso
   * HTTP/1.1 200 OK
   *   {
   *        ctr_id: 3,
   *        ctp_id : 2,
   *        ctt_id : 8,
   *        cts_id : 8,
   *        ctg_id : 8,
   *        ctc_id : 8,
   *        ctr_descricao : "TESTE TESTE",
   *        ctr_definicao : "TESTE TESTE",
   *        ctr_cobrade : "TESTE TESTE",
   *   }
   *  @apiSampleRequest /rest/crisis/type
   */
  app.get(uri+'/type', controller.listType);


  app.get(uri+'/type/class', controller.listTypeClass);
  app.get(uri+'/type/group/:ctc_id', controller.listTypeGroup);
  app.get(uri+'/type/subgroup/:ctc_id/:ctg_id', controller.listTypeSubGroup);
  app.get(uri+'/type/type/:ctc_id/:ctg_id/:cts_id', controller.listTypeType);
  app.get(uri+'/type/subtype/:ctc_id/:ctg_id/:cts_id/:ctt_id', controller.listTypeSubType);  

};
