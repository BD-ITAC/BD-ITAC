/*global app*/

app.service('UtilService', ['$rootScope', '$localStorage', function($rootScope, $localStorage){

  'use strict';
  var service = {};

  service.setSessionUser = function(user){
    $localStorage.user = {
      id: user.usu_id,
      name: user.usu_nome,
      email: user.usu_email
    };
    //$rootScope.$broadcast('headerMenuBroadcast');
  };

  service.clearStorage = function(){
    $localStorage.user = undefined;
    $rootScope.$broadcast('headerMenuBroadcast');
  };

  return service;
}]);
