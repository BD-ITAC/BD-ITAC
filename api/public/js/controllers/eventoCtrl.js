/*
 * @author: edizon
 */

'use strict';

app.controller('EventoController', ['$scope', '$rootScope', '$location', '$http','$localStorage','toastr','UtilService',
 function($scope,$rootScope,$location,$http,$localStorage, toastr, UtilService) {
    var vm = this;
    vm.form = {submitted : false};

    UtilService.setCurrentMenu($scope, $location);

    carregarDados();

    $scope.addEvento = function () {
      $http.post('/rest/parameters/evento',
         { 'descricao' : $scope.descricao, 'cid' : $scope.cid }).success(
            function(){
              carregarDados();
            }
          );
      $scope.descricao = '';
      $scope.cid ='';
    };

    $scope.removeEvento = function(data)
    {
      $http.delete('/rest/parameters/evento/' + data.id, data).success(
        function(err)
        {
          if(err.success === true)
          {
            carregarDados();
          }
          else {

          }
        }
      );
    };

    function carregarDados()
    {
      debugger;
      $http.get('/rest/parameters/evento').success(function(data){
                  $scope.ItemListCollection = data;
          });
    };

  }]);
