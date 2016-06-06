var supertest = require('supertest');
var should = require('should');

var  server = supertest.agent('http://localhost:3000');


describe('Test of Category', function(){

    it('deve listar categorias', function(done){

      server
      .get('/rest/categories')
      .end(function(err, res){

        res.status.should.equal(200);
        res.should.be.json;

        res.body.should.have.length(2);
        res.body[0].should.have.property('id');
        res.body[0].should.have.property('descricao');
        done();
      });
    });
});
