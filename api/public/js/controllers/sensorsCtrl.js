'use strict';
app.controller('SensorsController', ['$scope', '$rootScope', '$location', '$http',
  '$localStorage',
  'toastr','UtilService','Lightbox','ModalService',
  function($scope,$rootScope,$location,$http,$localStorage, toastr, UtilService, Lightbox, ModalService) {
    var vm = this;
    vm.form = {submitted : false};

    $scope.avisosSelecionados = [];
    $scope.avisosListCollection = [];
    
    UtilService.setCurrentMenu($scope, $location);

    $http.get('/rest/sensors').success(function(data){
                $scope.avisosListCollection = data;
    });
    

  }]);
