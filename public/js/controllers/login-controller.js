/*global app*/
'use strict';

app.controller('LoginController', ['$scope', '$rootScope', '$location', '$http','$localStorage','toastr','UtilService',
 function($scope,$rootScope,$location,$http,$localStorage, toastr, UtilService) {
    var vm = this;
    vm.form = {submitted : false};

    vm.login = function (form){

        vm.form.submitted = true;
        if (form.$valid) {
          var param = {
              email: form.email.$viewValue,
              password: form.password.$viewValue
         };
          $http.post('/rest/users/login/',param).then(function(res){
              if(res.data.success === true){
                  UtilService.setSessionUser(res.data.detail);
                  $rootScope.$broadcast('headerMenuBroadcast');
                  $location.path('/dash-board');
              }
          },function(err){
            console.error(err.data);
            toastr.error(err.data.statusText);
          });
      }else {
          toastr.info('Required fields not informed!');
      }
    };

}]);
