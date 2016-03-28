/*global app*/
'use strict';

app.controller('OneCtrl', ['$scope', '$rootScope', '$location', 'toastr', function($scope, $rootScope, $location, toastr)
{
   $scope.msg = function(){
      toastr.info('Teste de toast');

   };
   $rootScope.activetab = $location.path();
}]);
