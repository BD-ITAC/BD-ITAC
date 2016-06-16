var halson = require('halson');

module.exports = function(app){
  var controller = {};
  var alertsDAO = app.dao.alerts;

  controller.listAllAlerts = function(req, res, next){
    alertsDAO.listAllAlerts(function(err, data){
      if(err){
        res.status(500).json(err);
      }else{
        res.json(data);
      }
    });
  };

  /***
   * Retorno dos avisos
   */
   controller.listAlerts = function(req,res,next)
   {
     if(!req.query.hasOwnProperty('latitude') ||
      !req.query.hasOwnProperty('longitude') ||
        !req.query.hasOwnProperty('raio') ||
        !req.query.hasOwnProperty('timestamp')

      ) {
        var message = {
            "message" : {
              "id" : 1,
              "type" : "ERROR",
              "status" : "BADREQUEST",
              "description" : "Required fields not informed.",
              "info" : "BD-ITAC"
            }
          };
         res.status(401).json(message);
         return;
      }

     alertsDAO.listAlerts(req.query, function(err, data)
     {
		   if(err)
		   {
			   res.status(500).json(err);
		   }
		   else
		   {
			   if(data !== null && data.length >= 0){
           var resource = halson().addEmbed('alertsList',data);
           res.json(resource)
			   }
			   else
			   {
           var message = {
               "message" : {
                 "id" : 1,
                 "type" : "WARNING",
                 "status" : "NOTFOUND",
                 "description" : "Alerts not found",
                 "info" : "BD-ITAC"
               }
             };
				   res.status(404).json(message);
			   }
		   }
	   });
  };

  return controller;
};
