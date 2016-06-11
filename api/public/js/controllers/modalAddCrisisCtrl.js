/*
 * @author: edizon
 */

'use strict';
app.controller('addCrisisModalController',
  function($scope,$http,avisos,close, NgMap) {

    var vm = this;
    NgMap.getMap().then(function(map) {
        vm.map = map;
      });

  vm.googleMapsUrl = 'https://maps.google.com/maps/api/js';


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


    $scope.cidades = [];
    $scope.positions = [];

    for(var c in avisos)
    {
      var local=
      {
        cidade: avisos[c].cidade,
        estado: avisos[c].estado
      };


      $scope.cidades.push(local);
      $scope.positions.push([avisos[c].latitude,avisos[c].longitude]);
    }




    var latlongCentral =
    {
      latitude:avisos[0].latitude,
      longitude:avisos[0].longitude
    };

    $scope.retangulo = calcularRetanguloPeloCentro(latlongCentral,10000);
    $scope.lat = avisos[0].latitude;
    $scope.lon=  avisos[0].longitude;
    $scope.listaDeTiposDeCrises = true;
    $scope.btnSair = true;
    $scope.btnSalvar=true;

    //google.maps.event.trigger($scope.google.maps, 'resize');

    window.setTimeout(function(){
        google.maps.event.trigger($scope.google.maps, 'resize');
    }, 100);



  }

 function calcularRetanguloPeloCentro(latlong, distanciaEmMetros)
 {
   //Considerando o centro do retangulo como a coordenada central.
   var d = distanciaEmMetros/2;
   var lat0 = latlong.latitude;
   var lon0 = latlong.longitude;

   //http://stackoverflow.com/questions/2839533/adding-distance-to-a-gps-coordinate

   var lat1 = lat0 + (180/Math.PI)*(d/6378137)
   var lon1 = lon0 + (180/Math.PI)*(d/6378137)/Math.cos(Math.PI/180.0*lat0)

   var lat2 = lat0 + (180/Math.PI)*(-d/6378137)
   var lon2 = lon0 + (180/Math.PI)*(-d/6378137)/Math.cos(Math.PI/180.0*lat0)

   return [[lat2,lon2],[lat1,lon1]];
 };

  $scope.close = function(result, map) {
debugger;
    if(result === true)
    {
      // criar a crise na base de dados.
      var ne;
      ne = $scope.map.getBounds();
      console.log(ne);

    }
 	  close(result, 500); // close, but give 500ms for bootstrap to animate
  };

});
