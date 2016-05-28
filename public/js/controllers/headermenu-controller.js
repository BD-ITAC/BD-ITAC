/*global app*/
'use strict';

app.controller('HeaderMenuController', ['$scope', '$rootScope', '$location', '$http','$localStorage','toastr','UtilService',
 function($scope,$rootScope,$location,$http,$localStorage, toastr, UtilService) {
    var vm = this;
    vm.showMenu = false;
    vm.user = {};

    function displayMenu (param){
        vm.showMenu = false;
        if($localStorage.user !== undefined && $localStorage.user.name !== undefined){
          vm.user.name = $localStorage.user.name;
          vm.showMenu = true;
        }
    }

    vm.logout = function(){
      var name = $localStorage.user.name;
      $http.get('/rest/users/logout').then(function(response){
        if(response.status === 200){
          UtilService.clearStorage();
          $location.path('/index');
        }
      });
   };

    $scope.$on('headerMenuBroadcast', function (event) {
        displayMenu();
    });

}]);
