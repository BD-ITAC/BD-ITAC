module.exports = function(app){
  var controller = {};
  var crisisBusiness = app.business.crisis;

  /**
  * Registra uma nova crise
  * @author Danilo Ramalho
  * @param req HTTP request
  * @param res HHTP response
  */
  controller.saveCrisis = function(req, res, next){

    if(!req.body.hasOwnProperty('name') ||
    		!req.body.hasOwnProperty('email') ||
        !req.body.hasOwnProperty('phone') ||
        !req.body.hasOwnProperty('place') ||
        !req.body.hasOwnProperty('type') ||
        !req.body.hasOwnProperty('title')) {
          res.status(404).json({success: false, message: 'Required fields not informed.'});
      }else{
        crisis = {
          name : req.body.name,
          email: req.body.email,
          phone: req.body.phone,
          place: req.body.place,
          type: req.body.type,
          title: req.body.title,
          note: req.body.note
        };
        crisisBusiness.save(crisis, function(err, data) {
          if(err) {
              res.status(500).json(err);
          }else{
              res.json(data);
          }
        });
      }
 };


  var indicatorsDAO = app.dao.crisis;

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


  return controller;

};
