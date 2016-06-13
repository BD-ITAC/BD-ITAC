var supertest = require('supertest');
var should = require('should');

var  server = supertest.agent('http://bditac.ddns.net');


describe('Test of Category', function(){

    it('deve listar categorias', function(done){

      server
      .get('/rest/categories')
      .end(function(err, res){

        res.status.should.equal(200);
        res.should.be.json;

        res.body.should.have.property('_embedded');
        res.body._embedded.should.have.property('categoriaList');
        res.body._embedded.categoriaList[0].should.have.property('id');
        res.body._embedded.categoriaList[0].should.have.property('description');
        done();
      });
    });
});
