module.exports = function(app){
  var uri = '/rest/crisis';
  var controller = app.controllers.crisis;
  /**
   * @api {post} /rest/crisis Cadastrar
   * @apiGroup Crisis
   * @apiVersion 1.0.0
   *
   * @apiParam {String} descricao  Descricao da crise.
   * @apiParam {Numero{1-8}} categoria  Categoria da crise.
   * @apiParam {String} nome  Nome do usuário.
   * @apiParam {String} email  Email do usuário.
   * @apiParam {String} telefone  Telefone do usuário.
   * @apiParam {Numero} latitude  Latitude da localização.
   * @apiParam {Numero} longitude  Longitude da localização.
   * @apiParam {String} [fotografia]  Uma fotografia da crise.
   *
   * @apiParamExample {json} Request-Example:
   * {
   *    "descricao" : "Crise de teste",
   *    "categoria" : 0,
   *    "nome" : "João da Horta",
   *    "email" : "joao.horta@gmail.com",
   *    "telefone" : "(12) 95678-4321",
   *    "latitude" : 40.0,
   *    "longitude" : 50.0,
   *    "fotografia" : "77+9UE5HDQoaCgAAAA1JSERSAAAADAAAABAIBgAAACJh77+9BwAAAARnQU1BAADvv73vv70L77+9YQUAAAAgY0hSTQAAeiYAAO+/ve+/vQAA77+9AAAA77+977+9AAB1MAAA77+9YAAAOu+/vQAAF3Dvv73vv71RPAAAAAlwSFlzAAAXEgAAFxIBZ++/ve+/vVIAAAFZaVRYdFhNTDpjb20uYWRvYmUueG1wAAAAAAA8eDp4bXBtZXRhIHhtbG5zOng9ImFkb2JlOm5zOm1ldGEvIiB4OnhtcHRrPSJYTVAgQ29yZSA1LjQuMCI+CiAgIDxyZGY6UkRGIHhtbG5zOnJkZj0iaHR0cDovL3d3dy53My5vcmcvMTk5OS8wMi8yMi1yZGYtc3ludGF4LW5zIyI+CiAgICAgIDxyZGY6RGVzY3JpcHRpb24gcmRmOmFib3V0PSIiCiAgICAgICAgICAgIHhtbG5zOnRpZmY9Imh0dHA6Ly9ucy5hZG9iZS5jb20vdGlmZi8xLjAvIj4KICAgICAgICAgPHRpZmY6T3JpZW50YXRpb24+MTwvdGlmZjpPcmllbnRhdGlvbj4KICAgICAgPC9yZGY6RGVzY3JpcHRpb24+CiAgIDwvcmRmOlJERj4KPC94OnhtcG1ldGE+Ckzvv70nWQAAAk5JREFUKBVVUk1PE1EUPe+/ve+/ve+/vTDvv73vv71K77+9UEPvv71R77+90JTvv73vv70qau+/vQ0bE1bvv70FfwF/77+977+9QNyx77+9aEx077+9RkNY77+977+9BRpFTTDvv700FAUtae+/vX7vv71H77+9M++/vTMlanzvv71577+977+977+9e8+977+9au+/ve+/vVfvv70577+977+9aSLvv71KYVZo77+9z6ZlYe+/ve+/ve+/ve+/vV4XB++/ve+/ve+/ve+/ve+/ve+/vSgpFO+/vSfvv71OJDB377+9xIfvv70tVCPvv73vv73vv70yRu+/vWYR77+977+9Je+/vQbvv71B77+977+9SgU/X2/vv73vv73vv73vv71f77+977+977+9RzDvv71dRe+/vTjvv707DRpkS0rvv70e77+9MO+/vXAD77+9du+/ve+/ve+/ve+/vX3vv71yWVw6b++/vX1C77+977+9bDrvv73vv71ZEu+/ve+/ve+/vUYTRwTvv70/X++/ve+/ve+/vQLvv70uMGoa77+977+9Pj7vv70DEu+/vTEE77+9EO+/ve+/vS8w77+977+9Ru+/vXQF77+977+977+977+977+9dT3vv73vv70577+9BnBJEGDvv71cIt6C77+9JO+/vSbvv73vv73vv73ktIPvv71NzZrvv73vv73vv70EbhoD77+9YO+/vWYMZCIJIe+/vX3vv70WVnQHIgxQ77+977+9EEbvv70w3rjvv70jFe+/vRkM77+977+977+977+9DQZ4Mj3vv73vv73vv71777+9M++/ve+/vULvv71YJgLvv71F77+977+9Z++/ve+/vRAmJe+/ve+/vSJNEwHvv70LTO+/vXJiGk/vv70S77+9H2BAae+/vUXvv73vv70ifu+/vRbvv71E77+977+977+9Ue+/vSfvv73vv71F77+9Mu+/ve+/vRRL77+9IGnvv73vv71F77+977+977+9xLZIIEfvv73vv70x77+9HGlw77+977+977+9Imnvv73vv73vv71xbFzvv73vv73vv70477+977+9KxU6BO+/ve+/vXfvv70jdknvv73Qp3nvv716HTPvv73vv73vv718CXXvv73vv73vv73vv70GbO+/ve+/ve+/ve+/vRHvv70exqckGicgORrvv73vv73vv70o77+9L++/ve+/ve+/vU7vv70SRu+/vVfvv70R77+9AdG5NBTvv73vv73vv73vv70G77+9Lnd777+9f++/vUfvv73vv73VqlUYDO+/vWwk77+9Le+/vTTvv71zWu+/ve+/ve+/vVPvv73vv70MDu+/ve+/ve+/ve+/vT0+77+977+977+9Q++/ve+/vT4K77+9RSAzDu+/ve+/vXHvv73GtVgnzYt+A0fvv73vv70T77+9AO+/vWUAAAAASUVORO+/vUJg77+9"
   *  }
   *
   *
   * @apiSuccess {Numero} id Numero identificador da crise cadastrada
   * @apiSuccess {String} type Tipo da mensagem (INFO, WARNING, ERROR)
   * @apiSuccess {String} status Código de estado do sistema
   * @apiSuccess {String} description Descrição da mensagem
   * @apiSuccess {String} info Informações adicionais
   *
   * @apiSuccessExample {json} Sucesso
   * HTTP/1.1 201 Created
   *    {
   *   "message" : {
   *       "id" : 1,
   *       "type" : "INFO",
   *       "status" : "OK",
   *       "description" : "Crise registrada",
   *       "info" : "BD-ITAC"
   *     }
   *   }
   *
   */
  app.post(uri+'/', controller.saveCrisis);
  /**
   * @api {get} /rest/crisis Listar
   * @apiVersion 1.0.0
   * @apiGroup Crisis
   *
   * @apiSuccess {Numero} list.id Numero identificador.
   * @apiSuccess {String} list.description Descrição da evento.
   * @apiSuccess {Numero} list.categoria Categoria da evento.
   * @apiSuccess {String} list.nome Nome do usuario que enviou a evento.
   * @apiSuccess {String} list.email Endereço de email do usuario.
   * @apiSuccess {String} list.telefone Telefone do usuário.
   * @apiSuccess {Numero} list.latitude Latitude da localização.
   * @apiSuccess {Numero} list.langitude Longitude da localização.
   * @apiSuccess {String} list.fotografia Fotografia do evento em base64.
   *
   * @apiSuccessExample {json} Sucesso
   * HTTP/1.1 200 OK
   *    {
   *      {
   *          "id": 1,
   *          "descricao" : "Crise de teste",
   *          "categoria" : 1,
   *          "nome" : "João da Horta",
   *          "email" : "joao.horta@gmail.com",
   *          "telefone" : "(12) 95678-4321",
   *          "latitude" : 40.0,
   *          "longitude" : 50.0,
   *          "fotografia" : "...."
   *      }
   *      {
   *          "id":2,
   *          "descricao" : "Crise de teste2",
   *          "categoria" : 2,
   *          "nome" : "Neusa Japonesa",
   *          "email" : "japoneusa@gmail.com",
   *          "telefone" : "(12) 99999-4321",
   *          "latitude" : 40.0,
   *          "longitude" : 50.0,
   *          "fotografia" : "...."
   *      }
   *   }
   *  @apiSampleRequest /rest/crisis
   */
  app.get(uri, controller.listCrisis);
  /**
   * @api {get} /rest/crisis/nearbycrisis?latitude=-23.196641&longitude=-45.946840&raio=10 Pesquisa por coordenadas
   * @apiVersion 1.0.0
   * @apiGroup Crisis
   *
   * @apiParam {Number} latitude  Latitude da localização.
   * @apiParam {Number} longitude  Longitude da localização.
   * @apiParam {Number} raio Raio de alcance em metros.
   *
   *
   *
   * @apiSuccess {String} descricaoResumida Breve descrição do alerta.
   * @apiSuccess {String} descricaoCompleta Descrição detalhada do alerta.
   * @apiSuccess {String} categoriaAlerta Indica o tipo de alerta.
   * @apiSuccess {Number} origemLatitude Latitude do ponto de origem do alerta.
   * @apiSuccess {Number} origemLongitude Longitude do ponto de origem do alerta.
   * @apiSuccess {Number} origemRaioKms Área de abrangência do alerta em Kms.
   * @apiSuccess {URI} _links:self:href URI do link para o alerta.
   *
   * @apiSuccessExample {json} Sucesso
   * HTTP/1.1 200 OK
   *{
 *	  "_embedded" : {
 *	    "alertaList" : [ {
 *	      "descricaoResumida" : "Alagamento",
 *	      "descricaoCompleta" : "Deslizamento na na favela do Paraiso",
 *	      "categoriaAlerta" : 0,
 *	      "origemLatitude" : 40.0,
 *	      "origemLongitude" : 50.0,
 *	      "origemRaioKms" : 10.0,
 *	      "_links" : {
 *	        "self" : {
 *	          "href" : "http://localhost:3000/rest/crisis/nearbycrisis/16"
 *	        }
 *	      }
 *	    }, {
 *	      "descricaoResumida" : "Alagamento",
 *	      "descricaoCompleta" : "Deslizamento na na favela do Paraiso",
 *	      "categoriaAlerta" : 0,
 *	      "origemLatitude" : 40.0,
 *	      "origemLongitude" : 50.0,
 *	      "origemRaioKms" : 10.0,
 *	      "_links" : {
 *	        "self" : {
 *	          "href" : "http://localhost:3000/rest/crisis/nearbycrisis/2"
 *	        }
 *	      }
 *	    }, {
 *	      "descricaoResumida" : "Alagamento",
 *	      "descricaoCompleta" : "Crise de teste",
 *	      "categoriaAlerta" : 0,
 *	      "origemLatitude" : 40.0,
 *	      "origemLongitude" : 50.0,
 *	      "origemRaioKms" : 10.0,
 *	      "_links" : {
 *	        "self" : {
 *	          "href" : "http://localhost:3000/rest/crisis/nearbycrisis/18"
 *	        }
 *	      }
 *	    }, {
 *	      "descricaoResumida" : "Alerta de deslizamento",
 *	      "descricaoCompleta" : "Perigo de deslizamento na altura do Km 20 da rodovia Tamoios, pista Sao Jose dos Campos/Litoral",
 *	      "categoriaAlerta" : 0,
 *	      "origemLatitude" : 40.0,
 *	      "origemLongitude" : 50.0,
 *	      "origemRaioKms" : 1.0,
 *	      "_links" : {
 *	        "self" : {
 *	          "href" : "http://localhost:3000/rest/crisis/nearbycrisis/4"
 *	        }
 *	      }
 *	    }, {
 *	      "descricaoResumida" : "Alerta de teste",
 *	      "descricaoCompleta" : "Teste de alerta para verificar a funcionalidade do sistema",
 *	      "categoriaAlerta" : 0,
 *	      "origemLatitude" : 40.0,
 *	      "origemLongitude" : 50.0,
 *	      "origemRaioKms" : 1.0,
 *	      "_links" : {
 *	        "self" : {
 *	          "href" : "http://localhost:3000/rest/crisis/nearbycrisis/20"
 *	        }
 *	      }
 *	    }, {
 *	      "descricaoResumida" : "Alagamento",
 *	      "descricaoCompleta" : "Deslizamento na na favela do Paraiso",
 *	      "categoriaAlerta" : 0,
 *	      "origemLatitude" : 40.0,
 *	      "origemLongitude" : 50.0,
 *	      "origemRaioKms" : 10.0,
 *	      "_links" : {
 *	        "self" : {
 *	          "href" : "http://localhost:3000/rest/crisis/nearbycrisis/6"
 *	        }
 *	      }
 *	    }, {
 *	      "descricaoResumida" : "Alagamento",
 *	      "descricaoCompleta" : "Crise de teste",
 *	      "categoriaAlerta" : 0,
 *	      "origemLatitude" : 40.0,
 *	      "origemLongitude" : 50.0,
 *	      "origemRaioKms" : 10.0,
 *	      "_links" : {
 *	        "self" : {
 *	          "href" : "http://localhost:3000/rest/crisis/nearbycrisis/8"
 *	        }
 *	      }
 *	    }, {
 *	      "descricaoResumida" : "Alerta de teste",
 *	      "descricaoCompleta" : "Teste de alerta para verificar a funcionalidade do sistema",
 *	      "categoriaAlerta" : 0,
 *	      "origemLatitude" : 40.0,
 *	      "origemLongitude" : 50.0,
 *	      "origemRaioKms" : 1.0,
 *	      "_links" : {
 *	        "self" : {
 *	          "href" : "http://localhost:3000/rest/crisis/nearbycrisis/10"
 *	        }
 *	      }
 *	    }, {
 *	      "descricaoResumida" : "Alagamento",
 *	      "descricaoCompleta" : "Deslizamento na na favela do Paraiso",
 *	      "categoriaAlerta" : 0,
 *	      "origemLatitude" : 40.0,
 *	      "origemLongitude" : 50.0,
 *	      "origemRaioKms" : 10.0,
 *	      "_links" : {
 *	        "self" : {
 *	          "href" : "http://localhost:3000/rest/crisis/nearbycrisis/12"
 *	        }
 *	      }
 *	    }, {
 *	      "descricaoResumida" : "Alerta de deslizamento",
 *	      "descricaoCompleta" : "Perigo de deslizamento na altura do Km 20 da rodovia Tamoios, pista Sao Jose dos Campos/Litoral",
 *	      "categoriaAlerta" : 0,
 *	      "origemLatitude" : 40.0,
 *	      "origemLongitude" : 50.0,
 *	      "origemRaioKms" : 1.0,
 *	      "_links" : {
 *	        "self" : {
 *	          "href" : "http://localhost:3000/rest/crisis/nearbycrisis/14"
 *	        }
 *	      }
 *	    } ]
 *	  }
 *	}
 *
   *
   */
  app.get(uri+'/nearbycrisis', controller.nearbyCrisis);
  /**
   * @api {get} /rest/crisis/indicators Indicadores de crises
   * @apiVersion 1.0.0
   * @apiGroup Crisis
   *
   * @apiSuccess {Numero} cadastrados Quantidade de crises cadastradas.
   * @apiSuccess {Numero} finalizados Quantidade de crises finalizadas.
   * @apiSuccess {Numero} emcurso Quantidade de crises em curso.
   *
   * @apiSuccessExample {json} Sucesso
   * HTTP/1.1 200 OK
   *   {
   *        cadastrados: 30,
   *        finalizados : 20,
   *        emcurso : 8
   *   }
   *  @apiSampleRequest /rest/crisis/indicators
   */
  app.get(uri+'/indicators', controller.listIndicators);

};
