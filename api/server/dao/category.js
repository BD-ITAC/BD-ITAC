var categoryDao = null;


module.exports = function() {
  return categoryDao();
}

categoryDao = function() {

  var dao = {};

  var  categories = [];

  categories.push({id:1, descricao:'categoria 1'});
  categories.push({id:2, descricao:'categoria 2'});


  dao.listAll = function(callback) {
    return callback(null,categories);
  }
  return dao;

}
