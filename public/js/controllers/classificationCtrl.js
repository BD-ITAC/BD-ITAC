/*
 * @author: edizon
 */

'use strict';

app.controller('ClassificationController', ['$scope', '$rootScope', '$location', '$http','$localStorage','toastr','UtilService',
 function($scope,$rootScope,$location,$http,$localStorage, toastr, UtilService) {
    var vm = this;
    vm.form = {submitted : false};

    UtilService.setCurrentMenu($scope, $location);

    carregarDados();

    $scope.addClassificacao = function () {
      $http.post('/rest/parameters/classificacao',
         { 'descricao' : $scope.descricao }).success(
            function(){
              carregarDados();
            }
          );
      $scope.descricao = '';
    };

    $scope.removeClassificacao = function(data)
    {
      $http.delete('/rest/parameters/classificacao/' + data.id, data).success(
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
      $http.get('/rest/parameters/classificacao').success(function(data){
                  $scope.ClassificaListCollection = data;
          });
    };

  }]);
