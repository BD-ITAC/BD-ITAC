var supertest = require('supertest');
var should = require('should');

var  server = supertest.agent('http://bditac.ddns.net');


describe('Test of Alert', function(){

    it('Deve listar o historico de alertas', function(done){

      server
      .get('/#/alerts') //Url ainda nao implementada
      .end(function(err, res){

        res.status.should.equal(200);
        res.should.be.json;

        done();
      });
    });
});
