var mysql = require('mysql')
,connection  = require('express-myconnection')
, pool_connection
, env_conf = {
    development: {
      host: "edizon.onmypc.net",
      user: "root",
      password: "@Senha01",
      port: "3306",
      database: "bditac",
      connectionLimit    : 10,
      multipleStatements : true
    },
    production: {
      host: "localhost",
      user: "user_app",
      password: "User_@pp",
      port: "4000",
      database: "bditac",
      connectionLimit    : 10,
      multipleStatements : true
    }
};


module.exports = function(app){
  var conf = env_conf[process.env.NODE_ENV];


  if(!pool_connection){
    pool_connection = mysql.createPool(conf);
    app.use(
        connection(mysql,conf,'pool') //or single
    );
  }

 return pool_connection;

};
