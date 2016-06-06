var sinon = require('sinon');
var user = {login:function(err,data){}};
var dao = {user: user};
var app = {dao:dao};
var userController = require('../../../../server/controllers/user')(app);
var should = require("should");


describe('User controller', function() {
  describe('#login()', function() {

    var res = {
      status: function (x){
        return res;
      },
      json: function (x){
        return res;
      }
    };
    var user = {
      body : {
        email: 'teste@teste.com'
      }
    };

    it('should return error required fields', function(done) {

    userController.login(user, res, function(err, data) {
      data.success.should.equal(false);
    });
     done();
    });

    /*it('should return error user or password invalid', function(done){

      var stub = sinon.stub(app.dao.user, 'login');
      stub.returns( function(user, callback){
       return callback(null, {user: null});
      });

      user.body['password'] = 'teste';

      userController.login(user, res, function(err, data) {
        data.success.should.equal(false);
      });

      //stub.called.should.be.equal(true);
      done();
    });*/

  });
});
