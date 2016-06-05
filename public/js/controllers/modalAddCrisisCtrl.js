/*
 * @author: edizon
 */

'use strict';
app.controller('addCrisisModalController',
  function($scope,avisos,close) {
  debugger;

  //Carregar os tipos de crises.
  $scope.tiposdecrisesCollection = [
    {id:0, nome:"Terremoto"},
    {id:1, nome:"Balão no céu"},
    {id:2, nome:"Sei lá..."}



  ];

  if(!avisos.length)
  {
    $scope.btnSair = true;
    $scope.btnSalvar = false;
    $scope.message = "Nenhum Aviso Selecionado";
    $scope.msg = true;
  }
  else
  {
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
