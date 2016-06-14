var supertest = require('supertest');
var server = supertest.agent('http://bditac.ddns.net');
var theAccount = {
  "email": "adm@gmail.com",
  "password": "123"
};

exports.login = function (request, done) {
  server
    .post('/')
    .send(theAccount)
    .end(function (err, res) {
      if (err) {
        throw err;
      }
      server.saveCookies(res);
      done(server);
    });
};
