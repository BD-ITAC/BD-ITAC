/*
 * @author: edizon
 */

'use strict';

app.controller('CrisisDetailController', ['$scope', '$rootScope', '$location', '$http','$localStorage',
  'toastr','UtilService','Lightbox',
 function($scope,$rootScope,$location,$http,$localStorage, toastr, UtilService, Lightbox) {
    var vm = this;
    vm.form = {submitted : false};

    function loadCrisis(id){
      $http.get('/rest/crisis/detail/'+id).success(function(data){
        $scope.CrisisListCollection = data;
      });
    }

    UtilService.setCurrentMenu($scope, $location);
    loadCrisis(id);

    vm.goBack = function(cri_id){
      $location.path('#crisis');
    };

}]);
