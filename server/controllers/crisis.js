module.exports = function(app){
  var controller = {};
  var crisisBusiness = app.business.crisis;
  var indicatorsDAO = app.dao.crisis;

  /**
  * Registra uma nova crise
  * @author Danilo Ramalho
  * @param req HTTP request
  * @param res HHTP response
  */
  controller.saveCrisis = function(req, res, next){

        crisisBusiness.save(req.body, function(err, data) {
          if(err) {
            var status = err.validationError ? 400 : 500;
            res.status(status).json(err);
          }else{
              res.status(201).json(data);
          }
        });

 };


  /***
   * Retorno das crisis
   */
   controller.listCrisis = function(req,res,next)
   {
	   indicatorsDAO.listCrisis(function(err, data) {
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

controller.saveNearbyCrisis = function(req, res, next){

    if(!req.body.hasOwnProperty('place') ||
        !req.body.hasOwnProperty('type')) {
          res.status(400).json({success: false, message: 'Required fields not informed.'});
      }else{
        crisis = {
          place: req.body.place,
          type: req.body.type
        };
        crisisBusiness.alerts(crisis, function(err, data) {
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
