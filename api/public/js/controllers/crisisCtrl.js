/*
 * @author: edizon
 */

'use strict';

app.controller('CrisisController', ['$scope', '$rootScope', '$location', '$http','$localStorage',
  'toastr','UtilService','Lightbox',
 function($scope,$rootScope,$location,$http,$localStorage, toastr, UtilService, Lightbox) {
    var vm = this;
    vm.form = {submitted : false};

    function loadCrisis(){
      $http.get('/rest/crisis').success(function(data){
        $scope.CrisisListCollection = data;
      });
    }

    UtilService.setCurrentMenu($scope, $location);
    loadCrisis();

    /*$scope.openLightboxModal = function (index, fotos) {
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
    };*/

    vm.parseBoolString = function(x){
      return x == 1 ? 'Yes' : 'No';
    };

    vm.cancel = function(cri_id){
      $http({
      method: 'DELETE',
      url: '/rest/crisis/'+cri_id
      }).then(function (res) {
        toastr.success('successfully canceled crisis!');
        loadCrisis();
      }, function (err) {
        toastr.error('Could not cancel crisis, try again later!');
        console.warn(err);
      });
    };

}]);
