module.exports = function(app){
  var controller = {};
  var crisisDAO = app.dao.sensors;

   controller.list = function(req,res,next)
   {
     crisisDAO.listCrisis(function(err, data) {
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
