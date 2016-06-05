/*
 * @author: edizon
 */

'use strict';
app.controller('CrisisController', ['$scope', '$rootScope', '$location', '$http',
  '$localStorage',
  'toastr','UtilService','Lightbox','ModalService',
  function($scope,$rootScope,$location,$http,$localStorage, toastr, UtilService, Lightbox, ModalService) {
    var vm = this;
    vm.form = {submitted : false};

    $scope.avisosSelecionados = [];
    UtilService.setCurrentMenu($scope, $location);

    $http.get('/rest/crisis').success(function(data){
                $scope.CrisisListCollection = data;
    });

    $scope.openLightboxModal = function (index, fotos) {
        $scope.images = [];
          angular.forEach(fotos, function(value)
          {
              if(value != null && value)
              {
                  $scope.images.push({
                   'url': 'data:image/gif;base64,' + value,
                   'thumbUrl': 'data:image/gif;base64,' + value
                  });
              }
          });
          Lightbox.openModal($scope.images, index);
    };

    $scope.abrirCadastraCriseModal = function()
    {

      ModalService.showModal({
            templateUrl: "/part-views/addCrisis.html",
            controller: "addCrisisModalController",
            inputs: { avisos: $scope.avisosSelecionados}

      }).then(function(modal)
        {
          //it's a bootstrap element, use 'modal' to show it
          modal.element.modal();
          modal.close.then(function(result)
          {
            console.log(result);
          });
        });
    }

  }]);
