module.exports =  function(app){
  var controller = {};
  var dao = app.dao.category;
  controller.listAll = function(req, res, next){
    dao.listAll(function(err, data){
      res.json(data);
    });
  };

  return controller;

}
