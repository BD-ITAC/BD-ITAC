/*global app*/
'use strict';

app.controller('LoginController', ['$scope', '$rootScope', '$location', '$http','$localStorage',
 function($scope,$rootScope,$location,$http,$localStorage) {
    var vm = this;
/*
    vm.login = function (form){
        //vm.formProject.submitted = true;
        //if (form.$valid) {
          var param = {
              user: 'danl',
              password: 'teste'
         };
          $http.get('/rest/user/login/',param).then(function(response){
              console.log(response);
          },function(err){
            console.error(err.data);
          });
     // }
    };
*/
}]);
