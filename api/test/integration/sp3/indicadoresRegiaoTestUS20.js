var supertest = require('supertest');
var should = require('should');

var  server = supertest.agent('http://bditac.ddns.net');


describe('', function(){

    it('Deve listar os indicadores de crise', function(done){

      server
      .get('/rest/avisos/indicators') //Url ainda nao implementada
      .end(function(err, res){

        res.status.should.equal(200);
        res.should.be.json;

        done();
      });
    });
    
    it('Deve filtrar e listar os indicadores de crise por regiao', function(done){

      server
      .get('/rest/avisos/indicators') //Url ainda nao implementada
      .end(function(err, res){

        res.status.should.equal(600); //fail - precisa refatorar para filtrar por regiao
        res.should.be.json;

        done();
      });
    });
});
