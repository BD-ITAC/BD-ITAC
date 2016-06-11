module.exports = function(app){

  var uri = '/rest/categories';
  var controller = app.controllers.category;
  /**
   * @api {get} /rest/categories Listar
   * @apiVersion 1.0.0
   * @apiGroup Categorias
   *
   * @apiSuccess {Numero} categories.id Numero identificador.
   * @apiSuccess {String} categories.description Descrição do aviso.
   *
   * @apiSuccessExample {json} Sucesso
   * HTTP/1.1 200 OK
   * {_embedded:
         {categoriaList:
           [
               {
                  "id": "1",
                   "descricao" : "Alagamentos"
               }
               {
                   "id":"2",
                   "descricao" : "Emanação vulcânica"
               }
           ]
        }

    }
   *  @apiSampleRequest /rest/categories
   */
  app.get(uri, controller.listAll);




}
