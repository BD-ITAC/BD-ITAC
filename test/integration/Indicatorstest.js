var supertest = require("supertest");
var should = require("should");

var server = supertest.agent("http://localhost:3000");

describe("Teste de Indicadores",function(){

  it("Deve retornar 1 Indicador",function(done){

    // calling home page api
    server
    .get("/rest/crisis/indicators")
    .expect("Content-type",/json/)
    .expect(200) // THis is HTTP response
    .end(function(err,res){


      // HTTP status should be 200
      res.status.should.equal(200);
      res.should.be.json;
      // Error key should havebe false.      
      res.body[0].should.have.property('cadastrados');
      res.body[0].should.have.property('finalizados');
      res.body[0].should.have.property('emcurso');

      

      res.body.should.be.instanceof(Array).and.have.lengthOf(1);

      done();
    });
  });

});
