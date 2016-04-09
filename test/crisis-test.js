var supertest = require("supertest");
var should = require("should");

var server = supertest.agent("http://localhost:3000");

describe("Test of Crisis",function(){

  it("Deve retornar erro de falta de parametros informados",function(done){

    // calling home page api
    server
    .post("/rest/crisis")
    .send({'name': 'João', 'email': 'teste@teste.com'})
    .expect("Content-type",/json/)
    .set('Accept', 'application/json')
    .expect(404) // THis is HTTP response
    .end(function(err,res){

      res.should.be.json;
      // Error key should havebe false.
      res.body.should.have.property('success');
      res.body.success.should.equal(false);
      res.body.should.have.property('message');
      res.body.message.should.equal('Required fields not informed.');

      done();
    });
  });

  it("Deve retornar erro ao passar email inválido",function(done){

    var crisis_param = {
      name: 'Teste',
      email: 'testegmail.com',
      phone: 111111,
      place: 'Teste',
      type: 4,
      title: 'Teste'
    };

    // calling home page api
    server
    .post("/rest/crisis")
    .send(crisis_param)
    .expect("Content-type",/json/)
    .set('Accept', 'application/json')
    .expect(500) // THis is HTTP response
    .end(function(err,res){

      res.should.be.json;
      // Error key should havebe false.
      res.body.should.have.property('success');
      res.body.success.should.equal(false);
      res.body.should.have.property('message');
      res.body.message.should.equal('Invalid value data fields.');

      done();
    });
  });

  it("Deve retornar erro ao passar tipo de crise fora do intervalo de 1 à 8",function(done){

    var crisis_param = {
      name: 'Teste',
      email: 'teste@gmail.com',
      phone: 111111,
      place: 'Teste',
      type: 9,
      title: 'Teste'
    };

    // calling home page api
    server
    .post("/rest/crisis")
    .send(crisis_param)
    .expect("Content-type",/json/)
    .set('Accept', 'application/json')
    .expect(500) // THis is HTTP response
    .end(function(err,res){

      res.should.be.json;
      // Error key should havebe false.
      res.body.should.have.property('success');
      res.body.success.should.equal(false);
      res.body.should.have.property('message');
      res.body.message.should.equal('Invalid value data fields.');

      done();
    });
  });

  it("Deve retornar ok",function(done){

    var crisis_param = {
      name: 'Teste',
      email: 'teste@gmail.com',
      phone: 111111,
      place: 'Teste',
      type: 3,
      title: 'Teste'
    };

    // calling home page api
    server
    .post("/rest/crisis")
    .send(crisis_param)
    .expect("Content-type",/json/)
    .set('Accept', 'application/json')
    .expect(200) // THis is HTTP response
    .end(function(err,res){

      res.should.be.json;
      // Error key should havebe false.
      res.body.should.have.property('success');
      res.body.success.should.equal(true);
     

      done();
    });
  });

});
