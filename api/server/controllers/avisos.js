module.exports = function(app){
  var controller = {};
  var avisosBusiness = app.business.avisos;
  var indicatorsDAO = app.dao.avisos;

  /**
  * Registra um novo aviso
  * @author Danilo Ramalho
  * @param req HTTP request
  * @param res HHTP response
  */
  controller.saveAvisos = function(req, res, next){

        avisosBusiness.save(req.body, function(err, data) {
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
	   indicatorsDAO.listAvisos(function(err, data) {
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

  /**
  * Retorno indicadores
  */
  controller.listIndicators = function(req, res, next){
    indicatorsDAO.listAll(function(err, data) {
      if(err) {
          res.status(500).json(err);
      }else{
        if(data !== null && data.length >= 0) {
          res.json(data);
        }else {
          res.status(404).json({message:'Something went wrong.Please try again later.'});
        }
      }
    });
 };

  controller.nearbyCrisis = function(req, res, next){

     if(!req.query.hasOwnProperty('latitude') ||
      !req.query.hasOwnProperty('longitude') ||
        !req.query.hasOwnProperty('raio')) {
         res.status(404).json({success: false, message: 'Required fields not informed.'});
      }else{
        crisis = {
          latitude: req.query.latitude,
          longitude: req.query.longitude,
          raio: req.query.raio
        };
        crisisBusiness.nearbyAlerts(crisis, function(err, data) {
          if(err) {
              res.status(500).json(err);
          }else{
              res.json(data);
          }
        });
      }
 };


  return controller;

};
