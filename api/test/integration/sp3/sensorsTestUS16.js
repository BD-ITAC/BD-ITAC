var supertest = require('supertest');
var should = require('should');
var server = supertest.agent("http://bditac.ddns.net");
var login = require('../../../server/helper/login');

describe('Sensors test page', function () {

  var agent;

  before(function (done) {
    login.login(server, function (loginAgent) {
      agent = loginAgent;
      done();
    });
  });

  it('Deve listar página de sensores', function(done){

    server
    .get('/#/sensors')
    .end(function(err, res){
      res.status.should.equal(200);
      done();
    });
  });
});
