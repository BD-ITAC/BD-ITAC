var supertest = require('supertest');
var should = require('should');

var  server = supertest.agent('http://bditac.ddns.net');


describe('Test of Alert', function(){

    it('deve listar alertas processados', function(done){

      server
      .get('/rest/alerts') //Ainda não é a url definitiva
      .end(function(err, res){

        res.status.should.equal(200);
        res.should.be.json;

        //res.body.should.have.property('_embedded');
        //res.body._embedded.should.have.property('alertList');
        //res.body._embedded.categoriaList[0].should.have.property('id');
        //res.body._embedded.categoriaList[0].should.have.property('description');
        done();
      });
    });
});
