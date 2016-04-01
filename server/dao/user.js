module.exports = function(app){
  var dao = {};

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
