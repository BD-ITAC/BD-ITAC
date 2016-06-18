var halson = require('halson');

module.exports = function(app){
  var controller = {};
  var avisosBusiness = app.business.avisos;
  var avisosDAO = app.dao.avisos;

  /**
  * Registra um novo aviso
  * @author Danilo Ramalho
  * @param req HTTP request
  * @param res HHTP response
  */
  controller.saveAvisos = function(req, res, next){

        avisosBusiness.save(req,function(err, data) {
          if(err) {
            var status = err.validationError ? 400 : 500;
            res.status(status).json(err);
          }else{
              res.status(201).json(data);
          }
        });

 };


  /***
   * Retorno dos avisos
   */
   controller.listAvisos = function(req,res,next)
   {
     var filtro = "";

     if(req.query.hasOwnProperty('id'))
     {
       filtro = req.query.id;
     }
	   avisosDAO.listAvisos(filtro,function(err, data) {
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

   controller.buscarPorId = function(req,res,next)
   {
     var id = req.params.id
    avisosDAO.buscarPorId(id,function(err, data) {
      if(err)
      {

        res.status(500).json(err);
      }
      else
      {
        if(data !== null){
          res.json(data);
        }
        else
        {
          var message = {
              "message" : {
                "id" : 1,
                "type" : "WARNING",
                "status" : "NOTFOUND",
                "description" : "Warning not found",
                "info" : "BD-ITAC"
              }
            };
          res.status(404).json(message);
        }
      }
    });
   };


   /**
   * Retorno indicadores
   */
   controller.listIndicators = function(req, res, next){
     avisosDAO.listIndicators(function(err, indicadorList) {
       if(err) {
           res.status(500).json(err);
       }else{

           if(indicadorList !== null && indicadorList.length >= 0){
             //HAL json
             var resource = halson().addEmbed('indicadorList',indicadorList);
             res.json(resource)

           }
         else {
           res.status(404).json({message:'Something went wrong.Please try again later.'});
         }
       }
     });
  };

  controller.nearbyWarnings = function(req, res, next){

     if(!req.query.hasOwnProperty('latitude') ||
      !req.query.hasOwnProperty('longitude') ||
        !req.query.hasOwnProperty('raio') ||
        !req.query.hasOwnProperty('timestamp')

      ) {
         res.status(404).json({success: false, message: 'Required fields not informed.'});
      }else{
        aviso = {
          latitude: req.query.latitude,
          longitude: req.query.longitude,
          raio: req.query.raio,
          timestamp: req.query.timestamp.replace("T", " ")

        };
        avisosDAO.nearbyWarnings(aviso, function(err, alertaList) {
          if(err) {
              res.status(500).json(err);
          }else{
              res.json(halson().addEmbed('alertaList',alertaList));
          }
        });
      }
 };


  return controller;

};
