var halson = require('halson');

module.exports = function(app){
  var controller = {};
  var alertsDAO = app.dao.alerts;

  /***
   * Retorno dos avisos
   */
   controller.listAlerts = function(req,res,next)
   {
     alertsDAO.listAlerts(function(err, data)
     {
		   if(err)
		   {
			   res.status(500).json(err);
		   }
		   else
		   {
			   if(data !== null && data.length >= 0){
           var resource = halson().addEmbed('alertsList',indicadorList);
           res.json(resource)
			   }
			   else
			   {
				   res.status(404).json({message:'Something went wrong. Please Try again later.'});
			   }
		   }
	   });
   };

  return alertsDAO;
};
