/*global app*/
'use strict';

app.controller('LoginController', ['$scope', '$rootScope', '$location', '$http','$localStorage','toastr','UtilService',
 function($scope,$rootScope,$location,$http,$localStorage, toastr, UtilService) {
    var vm = this;
    vm.form = {
        email : 'adm@gmail.com',
        password : '123',
        submitted : false
    };

    angular.element(document).ready(function () {
        if($localStorage.user !== undefined && $localStorage.user.name !== undefined){
            $location.path('/dash-board');
        }
    });


    vm.login = function (form){
        vm.form.submitted = true;
        if (form.$valid) {
          var param = {
              email: form.email.$viewValue,
              password: form.password.$viewValue
         };
          $http.post('/rest/users/login',param).then(function(res){
              if(res.data.success === true){
                  UtilService.setSessionUser(res.data.detail);
                  $rootScope.$broadcast('headerMenuBroadcast');
                  $location.path('/dash-board');
              }
          },function(err){
            toastr.error(err.data.message);
          });
      }else {
          toastr.info('Required fields not informed!');
      }
    };

}]);
