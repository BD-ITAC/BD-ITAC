'use strict';

// Set default node environment to development
process.env.NODE_ENV = process.env.NODE_ENV || 'development';

var http = require('http'),
    app = require('./config/express')();


/**************************
 *** DAO Iniatilization ***
 **************************/

var connection  = require('express-myconnection');
var mysql = require('mysql');

/**
 * Pool usado nos componentes de acesso a dados.
 */
var connConfig = {
    connectionLimit    : 10,
    multipleStatements : true,
    host               : 'localhost',
    user               : 'root',
    password           : 'root',
    database           : 'bditac'
};

var pool  = mysql.createPool(connConfig);
app.use(
/*conexao remota*/
    connection(mysql,connConfig,'pool') //or single
);

// inicia os DAOs.
require("./server/dao/crisis")(pool);


http.createServer(app).listen(app.get('port'), function(){
  console.log('Express server listening on port ' + app.get('port'));
});
