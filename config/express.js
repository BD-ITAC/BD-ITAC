'use strict';

/**
 * Module dependencies.
 */
var express = require('express');
var load = require('express-load');
var bodyParser = require('body-parser');

module.exports = function(){
  var app = express();
    // all environments
  app.set('port', process.env.PORT || 3000);

  app.use('/bower_components',  express.static(__dirname + '/bower_components'));
  app.use(express.static('./public/'));
  app.set('views', './views');
  app.set('view engine', 'ejs');
  app.use(bodyParser.urlencoded({extended: true}));
  app.use(bodyParser.json());

  load('models', {cwd: 'server'})
//    .then('helpers')
    .then('dao')
    .then('business')
    .then('controllers')
    .then('routes')
    .into(app);

    return app;
};
