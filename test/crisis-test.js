var supertest = require("supertest");
var should = require("should");

var server = supertest.agent("http://localhost:3000");

describe("Test of Crisis",function(){

  it("Deve retornar erro de falta de parametros informados",function(done){

    // calling home page api
    server
    .post("/rest/crisis")
    .send({'name': 'Java', 'email': 'teste@teste.com'})
    .expect("Content-type",/json/)
    .expect(404) // THis is HTTP response
    .end(function(err,res){

      res.should.be.json;
      // Error key should havebe false.
      res.body.should.have.property('success');
      res.body.should.have.property('message');
      res.body.message.should.equal('Required fields not informed.');

      done();
    });
  });

});
