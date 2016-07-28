var db = require("../db/transacao");

var userDAO = null;

  // Inicialização do objeto, obrigando passagem de POOL como parametro.
  // Retorna um objeto crisisDAO implementado logo em seguida.
module.exports = function(app) {
    return userDAO(app.db.conexao);
};

userDAO = function(pool) {
    var self = this;
    if (pool !== null) {
        this.pool = pool;
    }

  var dao = {};
  /**
  * Realiza o login do usuário
  * @author Danilo Ramalho
  * @param string email, string password
  */
  dao.login = function(user, callback){

      self.pool.query('SELECT * FROM usuario WHERE usu_app=1 AND usu_email = ? AND usu_pwd= ?', [user.email, user.password], function(err, rows){
        if(err){
          callback (err, {});
        }else{
          //user = (rows ? rows[0] : null);
          var user = null;
          if(rows != null && rows.length >0)
          {
            user =
            {
              usu_nome:  rows[0].usu_nm,
              usu_id: rows[0].usu_id,
              usu_email: rows[0].usu_email,
              usu_fone: rows[0].usu_fone
            };
          }
          callback(null, user);
        }
      });
  };

  /**
  Criação do mock json
  **/
  var users = [];
  users.push({
               id: 1,
               name: "João Silva",
               email: "joao.silva@gmail.com"
            });
  users.push({
               id: 2,
               name: "Maria Silva",
               email: "maria.silva@gmail.com"
            });

  /**
  * Realiza as query de consulta de usuários no BD
  * @author Danilo Ramalho
  * @param nothing parameters
  */
  dao.listAll = function(callback){
    return callback(null, users);
  };

  return dao;

};
