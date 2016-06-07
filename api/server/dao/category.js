var db = require("../db/transacao");
var categoryDao = null;

module.exports = function(app) {
  return categoryDao(app.db.conexao);
}

categoryDao = function(pool) {
    var self = this;
    if (pool !== null) {
        this.pool = pool;
    }

  var dao = {};

  dao.listAll = function(callback){
    var query =
    "SELECT           \
        cat_id , \
        cat_ds\
      FROM categoria        \
     ORDER BY cat_ds";

    self.pool.query(
       query,
       function(err, rows){
      if(err){
        callback(err,{});
      }else{
        callback(null, getDados(rows));
      }
    });
  };

  function getDados(rows)
  {
    var categorias = [];
    for(c in rows){
      var categoria =
      {
        id: rows[c].cat_id,
        description: rows[c].cat_ds
      }

      categorias.push(categoria);
    }

    console.log(categorias);
    return categorias;
  }

  return dao;

}
