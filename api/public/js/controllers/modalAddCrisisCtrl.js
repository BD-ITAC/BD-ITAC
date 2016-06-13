/*
 * @author: edizon
 */

'use strict';
app.controller('addCrisisModalController',
  function($scope,$http,$window,avisos,close, NgMap) {

    var vm = this;



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
    $scope.avisosIds = [];

    for(var c in avisos)
    {
      var local=
      {
        cidade: avisos[c].cidade,
        estado: avisos[c].estado
      };

      $scope.avisosIds.push(avisos[c].id);
      $scope.cidades.push(local);
      $scope.positions.push([avisos[c].latitude,avisos[c].longitude]);
    }

    //Tem que calcular a média. Não somente a primeira posição.
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
    NgMap.getMap().then(function(map) {
            vm.map = map;
            window.setTimeout(function(){
                google.maps.event.trigger(map, 'resize');
                var myLatLng = new google.maps.LatLng(avisos[0].latitude, avisos[0].longitude);
                $scope.map.setCenter(myLatLng);
            }, 100);

      });

      vm.googleMapsUrl = 'https://maps.google.com/maps/api/js';



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

   return [[lat2,lon2],[lat2,lon1],[lat1,lon1],[lat1,lon2]];
 };

  $scope.close = function(result, map) {

    if(result === true)
    {

      if($scope.descricao && !$scope.searchBoxData.value)
      {


      // criar a crise na base de dados.
      var paths;
      paths = $scope.map.shapes[0].getPath();

    var coords = "";
      // Iterate over the vertices.
    for (var i =0; i < paths.getLength(); i++) {
      var xy = paths.getAt(i);
      coords += xy.lat() + "," + xy.lng() + ",";

    }

      coords = coords.substring(1, coords.length-1);



      var parameter = JSON.stringify({
          regiao_coords:coords,
          descricao: $scope.descricao,
          cidade:$scope.cidades[0].cidade,
          tipo:$scope.searchBoxData.id,
          geoid:"3",
          avisosId: $scope.avisosIds
      });

    $http.post('/rest/crisis', parameter).
    success(function(data, status, headers, config) {
        // this callback will be called asynchronously
        // when the response is available
        $window.alert('Crise criada com sucesso!');
      }).
      error(function(data, status, headers, config) {
        // called asynchronously if an error occurs
        // or server returns response with an error status.
        $window.alert('Houve um erro com a solicitação. A crise não foi criada.');
      });
        close(result, 500); // close, but give 500ms for bootstrap to animate
      }
      else {
        $window.alert('Dados Incompletos.');
        return;
      }

    }
    else {
        $window.alert('Nenhuma alteração registrada.');
        close(result, 500);
    }

  };

});
