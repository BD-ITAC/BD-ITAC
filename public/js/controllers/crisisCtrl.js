/*
 * @author: edizon
 */

'use strict';

app.controller('CrisisController', ['$scope', '$rootScope', '$location', '$http','$localStorage',
  'toastr','UtilService','Lightbox',
 function($scope,$rootScope,$location,$http,$localStorage, toastr, UtilService, Lightbox) {
    var vm = this;
    vm.form = {submitted : false};

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


}]);
