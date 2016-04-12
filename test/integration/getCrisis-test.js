var app = require('../../app');
var supertest = require("supertest");
var should = require("should");

var server = supertest.agent("http://localhost:3000");

describe("Teste de Listagem de Alertas de Crises",function(){

  it("Deve retornar alertas de crises",function(done){

    // calling home page api
    server
    .get("/rest/crisis/getCrisis")
    .expect("Content-type",/json/)
    .expect(200) // THis is HTTP response
    .end(function(err,res){


      // HTTP status should be 200
      res.status.should.equal(200);
      res.should.be.json;
      // Error key should have be false.
      res.body[0].should.have.property('name');
      res.body[0].should.have.property('email');
      res.body[0].should.have.property('phone');
      res.body[0].should.have.property('place');
      res.body[0].should.have.property('type');
      res.body[0].should.have.property('title');
      res.body[0].should.have.property('note');

      done();
    });
  });

});
