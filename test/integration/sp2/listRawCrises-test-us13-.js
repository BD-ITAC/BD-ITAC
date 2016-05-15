var supertest = require("supertest");
var should = require("should");

var server = supertest.agent("http://localhost:3000");

describe("Crises list test",function(){

  it("Returns HTTP status == 200, '/rest/crises'",function(done){

    // calling crises list page
    server
    .get("/rest/crisis")
    .expect(200) // This is HTTP response
    .end(function(err,res){

      // HTTP status should be 200
      res.status.should.equal(200);

      done();
    });
  });

    it("Returns HTTP status == 404, '/invalidUrl'",function(done){

    // calling crises list page
    server
    .get("/invalidUrl")
    .expect(404) // THis is HTTP response
    .end(function(err,res){

      // HTTP status should be 404
      res.status.should.equal(404);

      done();
    });
  });

});
