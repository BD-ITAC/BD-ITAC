//var b=  require('../server/business/business')();
var sinon = require('sinon');
var app = require('../config/express')();
var crisisBusiness = app.business.crisis;
var should = require("should");


describe('Crisis business', function() {
  describe('#save()', function() {
  	 
    it('should save without error', function(done) {
     // var business = app.business.crisis;
      var crisis = {
      name: 'Teste',
      email: 'teste@gmail.com',
      phone: 111111,
      place: 'Teste',
      type: 3,
      title: 'Teste'
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