/*global app*/

app.service('UtilService', ['$rootScope', '$localStorage', function($rootScope, $localStorage){

  'use strict';
  var service = {};

  service.setSessionUser = function(user){
    $localStorage.user = {
      id: user.usu_id,
      name: user.usu_nome,
      email: user.usu_email,
      telefon: user.usu_fone
    };
    //$rootScope.$broadcast('headerMenuBroadcast');
  };

  service.clearStorage = function(){
    $localStorage.user = undefined;
    $rootScope.$broadcast('headerMenuBroadcast');
  };

  service.setCurrentMenu = function($scope, $location){
    $scope.location = $location;
    $scope.$watch('location.url()', getTitle);
    function getTitle() {
      $scope.pageTitle = $location.url().substring(1);
    }

    $rootScope.$broadcast('headerSeCurrentMenuBroadcast', $scope.location.$$path);

  };

  return service;
}]);
