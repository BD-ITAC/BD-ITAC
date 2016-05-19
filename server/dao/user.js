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

      self.pool.query('SELECT * FROM usuario WHERE usu_email = ?', [user.email], function(err, rows){
        if(err){
          callback (err, {});
        }else{
          user = (rows ? rows[0] : null);
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
