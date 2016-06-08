/*
 * @author: edizon
 */

'use strict';
app.controller('addCrisisModalController',
  function($scope,$http,avisos,close) {



  if(!avisos.length)
  {
    $scope.btnSair = true;
    $scope.btnSalvar = false;
    $scope.message = "Nenhum Aviso Selecionado";
    $scope.msg = true;
  }
  else
  {
    $http.get('/rest/categories').success(function(data){
                $scope.tiposdecrisesCollection = data;
    });
  debugger;

    $scope.cidade = avisos[0].cidade;
    $scope.latitude = avisos[0].latitude;
    $scope.longitude = avisos[0].longitude;
    $scope.listaDeTiposDeCrises = true;
    $scope.btnSair = true;
    $scope.btnSalvar=true;

  }



  $scope.close = function(result) {
    if(result === true)
    {
      // criar a crise na base de dados.

    }
 	  close(result, 500); // close, but give 500ms for bootstrap to animate
  };

});
