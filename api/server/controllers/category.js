var halson = require('halson');

module.exports =  function(app){
  var controller = {};
  var dao = app.dao.category;

  controller.listAll = function(req,res,next){

    dao.listAll(function(err, categoriaList){
      if(err)
      {
        res.status(500).json(err);
      }
      else
      {
        if(categoriaList !== null && categoriaList.length >= 0){

          //HAL json
          var resource = halson().addEmbed('categoriaList',categoriaList);
          res.json(resource)

        }
        else
        {
          res.status(404).json({message:'Something went wrong. Please Try again later.'});
        }
      }
    });

  };

  return controller;

}
