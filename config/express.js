'use strict';

/**
 * Module dependencies.
 */
var express = require('express');
var load = require('express-load');
var bodyParser = require('body-parser');
var session = require('express-session');

module.exports = function(){
  var app = express();
    // all environments
  app.set('port', process.env.PORT || 3000);

  //config session
  app.use(session({secret: 'ssshhhhh',saveUninitialized: true,resave: true}));

  app.use('/bower_components',  express.static('./bower_components'));
  app.use(express.static('./public/'));
  app.set('views', './views');
  app.set('view engine', 'ejs');
  app.use(bodyParser.urlencoded({extended: true}));
  app.use(bodyParser.json());

  load('models', {cwd: 'server'})
//    .then('helpers')
    .then('db')
    .then('dao')
    .then('business')
    .then('controllers')
    .then('routes')
    .into(app);

    return app;
};
