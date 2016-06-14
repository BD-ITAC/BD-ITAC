var sinon = require('sinon');
var avisos = {save:function(err,data){}};
var dao = {avisos: avisos};
var app = {dao:dao};
var avisosBusiness = require('../../../../server/business/avisos')(app);
var should = require("should");

var stub = sinon.stub(app.dao.avisos,'save');

describe('Avisos business', function() {
  describe('#save()', function() {

    it('should save without error', function(done) {

      var avisos = {
        descricao: 'Teste',
        categoria: 3,
        nome: 'Teste',
        email: 'teste@gmail.com',
        telefone: 111111,
        latitude: 40.0,
        longitude: 50.0,
        fotografia:''

    };



    stub.returns( function(avisos, callback){
     return callback(null, {status: 'ok'});
    });


     avisosBusiness.save(avisos, function(err, data) {

      });

      stub.called.should.be.equal(true);

     done();
    });



    it('should save without error 2', function(done) {

      var avisos = {
        "descricao" : "Deslizamento na na favela do Paraiso",
         "categoria" : 1,
         "nome" : "Ze das Couves",
         "email" : "zedascouves@gmail.com",
         "telefone" : "(12) 99876-1234",
         "latitude": 50,
         "longitude": 50

    };



    stub.returns( function(avisos, callback){
     return callback(null, {status: 'ok'});
    });


     avisosBusiness.save(avisos, function(err, data) {

      });

      stub.called.should.be.equal(true);

     done();
    });

  });
});
