var halson = require('halson');

module.exports = function(app){
  var controller = {};
  var avisosBusiness = app.business.avisos;
  var avisosDAO = app.dao.avisos;
  
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
          res.status(404).json({message:'Something went wrong. Please Try again later.'});
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