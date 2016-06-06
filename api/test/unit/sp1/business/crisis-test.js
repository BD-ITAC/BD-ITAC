var sinon = require('sinon');
var crisis = {save:function(err,data){}};
var dao = {crisis: crisis};
var app = {dao:dao};
var crisisBusiness = require('../../../../server/business/crisis')(app);
var should = require("should");


describe('Crisis business', function() {
  describe('#save()', function() {

    it('should save without error', function(done) {

      var crisis = {
        descricao: 'Teste',
        categoria: 3,
        nome: 'Teste',
        email: 'teste@gmail.com',
        telefone: 111111,
        latitude: 40.0,
        longitude: 50.0,
        fotografia:''

    };


    var stub = sinon.stub(app.dao.crisis,'save');
    stub.returns( function(crisis, callback){
     return callback(null, {status: 'ok'});
    });


     crisisBusiness.save(crisis, function(err, data) {

      });

      stub.called.should.be.equal(true);

     done();
    });
  });
});
