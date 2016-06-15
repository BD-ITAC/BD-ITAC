var supertest = require('supertest');
var should = require('should');

var  server = supertest.agent('http://bditac.ddns.net');


describe('Teste dos Avisos', function(){

    it('Deve listar pagina de avisos', function(done){

      server
      .get('/rest/avisos') //Ainda não é a url definitiva
      .end(function(err, res){

        res.status.should.equal(200);
        res.should.be.json;

        done();
      });
    });
    
    it('Deve verificar existencia das categorias de aviso', function(done){

      server
      .get('/rest/avisos/1') //Ainda não é a url definitiva
      .end(function(err, res){

        res.status.should.equal(200);
        res.should.be.json;

        res.body.should.have.property('id');
        res.body.should.have.property('nome');
        res.body.should.have.property('telefone');
        res.body.should.have.property('email');
        res.body.should.have.property('descricao');
        res.body.should.have.property('latitude');
        res.body.should.have.property('longitude');
        res.body.should.have.property('cidade');
        res.body.should.have.property('estado');
        res.body.should.have.property('status');

        done();
      });
    });
});
