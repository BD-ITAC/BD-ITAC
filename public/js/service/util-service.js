/*global app*/

app.service('UtilService', ['$rootScope', '$localStorage', function($rootScope, $localStorage){

  'use strict';
  var service = {};

  service.setSessionUser = function(user){
    $localStorage.user = {
      id: user.id,
      name: user.name,
      email: user.email
    };
    //$rootScope.$broadcast('headerMenuBroadcast');
  };

  service.clearStorage = function(){
    $localStorage.user = undefined;
    $rootScope.$broadcast('headerMenuBroadcast');
  };

  return service;
}]);
