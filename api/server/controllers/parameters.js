module.exports = function(app){
  var controller = {};

  var parametersDAO = app.dao.parameters;

   var parameterBusiness = app.business.parameters;

  /*******
   * Salvar Parametros Classificação
   * @author: Edizon
   ********/
   controller.saveclassificacao = function(req, res, next){
    parameterBusiness.saveClassificacao(req.body, function(err, data) {
      if(err) {
        var status = err.validationError ? 400 : 500;
        res.status(status).json(err);
      }else{
          res.status(201).json(data);
      }
    });
  };

  controller.saveEvento = function(req, res, next){
   parameterBusiness.saveEvento(req.body, function(err, data) {
     if(err) {
       var status = err.validationError ? 400 : 500;
       res.status(status).json(err);
     }else{
         res.status(201).json(data);
     }
   });
  };

  /*******
   * Apagar Parametros Classificação
   * @author: Edizon
   ********/
   controller.deleteClassificacao = function(req, res){
     var key = req.params.id
     parameterBusiness.deleteClassificacao(key, function(err) {
       if(err) {
         var status = err.validationError ? 400 : 500;
         res.status(status).json({success: false, message: + err});
       }else{
           res.status(201).json({success: true, message: 'Item deleted successfully'});
       }
     });
   };
   controller.deleteEvento = function(req, res){
     var key = req.params.id
     parameterBusiness.deleteEvento(key, function(err) {
       if(err) {
         var status = err.validationError ? 400 : 500;
         res.status(status).json({success: false, message: + err});
       }else{
           res.status(201).json({success: true, message: 'Item deleted successfully'});
       }
     });
   };

  /*******
   * Retorno dos Parametros Classificação
   * @author: Edizon
   ********/

  controller.listClassificacao = function(req,res,next)
  {
    parametersDAO.listClassificacao(function(err, data) {
      if(err)
      {
        res.status(500).json(err);
      }
      else
      {
        if(data !== null && data.length >= 0){
          res.json(data);
        }
        else
        {
          res.status(404).json({message:'Something went wrong. Please Try again later.'});
        }
      }
    });
  };
  controller.listEvento = function(req,res,next)
  {
    parametersDAO.listEvento(function(err, data) {
      if(err)
      {
        res.status(500).json(err);
      }
      else
      {
        if(data !== null && data.length >= 0){
          res.json(data);
        }
        else
        {
          res.status(404).json({message:'Something went wrong. Please Try again later.'});
        }
      }
    });
  };

  return controller;
};
