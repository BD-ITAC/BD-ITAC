var supertest = require("supertest");
var should = require("should");

var server = supertest.agent("http://edizon.onmypc.net:3000");

describe("Teste de Servidor - http://edizon.onmypc.net:3000",function(){

  it("Deve HTTP status == 200, '/'",function(done){

    // calling home page api
    server
    .get("/")
    .expect("Content-type",/json/)
    .expect(200) // THis is HTTP response
    .end(function(err,res){

      // HTTP status should be 200
      res.status.should.equal(200);

      done();
    });
  });

    it("Deve HTTP status == 404, '/invalidUrl'",function(done){

    // calling home page api
    server
    .get("/invalidUrl")
    .expect("Content-type",/json/)
    .expect(404) // THis is HTTP response
    .end(function(err,res){

      // HTTP status should be 404
      res.status.should.equal(404);

      done();
    });
  });

});
