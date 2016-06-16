var supertest = require('supertest');
var should = require('should');

var  server = supertest.agent('http://bditac.ddns.net');


describe('', function(){

    it('Deve listar os indicadores de crise', function(done){

      server
      .get('/rest/avisos/indicators')
      .end(function(err, res){

        res.status.should.equal(200);
        res.should.be.json;

        done();
      });
    });
    
    it('Deve filtrar e listar os avisos por regiao', function(done){

      server
      .get('/rest/avisos/nearbyWarnings?latitude=40&longitude=50&raio=10&timestamp="2016/06/12T02:00:00"')
      .end(function(err, res){

        res.status.should.equal(200);
        res.should.be.json;
        
        done();
      });
    });
});
