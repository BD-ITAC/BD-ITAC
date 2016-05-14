var supertest = require("supertest");
var should = require("should");

var server = supertest.agent("http://localhost:3000");

describe("Teste de Usuarios",function(){

  it("Deve retornar dois usuarios",function(done){

    // calling home page api
    server
    .get("/rest/users")
    .expect("Content-type",/json/)
    .expect(200) // THis is HTTP response
    .end(function(err,res){


      // HTTP status should be 200
      res.status.should.equal(200);
      res.should.be.json;
      // Error key should havebe false.
      res.body[0].should.have.property('id');
      res.body[0].should.have.property('name');
      res.body[0].should.have.property('email');

      res.body[1].should.have.property('id');
      res.body[1].should.have.property('name');
      res.body[1].should.have.property('email');

      res.body.should.be.instanceof(Array).and.have.lengthOf(2);

      done();
    });
  });

});
