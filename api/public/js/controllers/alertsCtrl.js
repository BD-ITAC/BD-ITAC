/*
 * @author: danilo.ramalho
 */

'use strict';

app.controller('AlertsController', ['$scope', '$rootScope', '$location', '$http','$localStorage',
  'toastr','UtilService','Lightbox',
 function($scope,$rootScope,$location,$http,$localStorage, toastr, UtilService, Lightbox) {
    var vm = this;
    vm.form = {submitted : false};

    function loadAlerts(){
      $http.get('/rest/alerts/all').success(function(data){
        $scope.AlertsListCollection = data;
      });
    }

    UtilService.setCurrentMenu($scope, $location);
    loadAlerts();

}]);
