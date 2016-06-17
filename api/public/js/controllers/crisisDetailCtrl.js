/*
 * @author: edizon
 */

'use strict';

app.controller('CrisisDetailController', ['$scope', '$rootScope', '$location', '$http','$localStorage',
  'toastr','UtilService','Lightbox', '$routeParams',
 function($scope,$rootScope,$location,$http,$localStorage, toastr, UtilService, Lightbox, $routeParams) {
    var vm = this;
    vm.form = {submitted : false};
    vm.cri_id = $routeParams.cri_id;

    function initialize(avisos, regiao) {

      var myOptions = {
          center: new google.maps.LatLng(45.4555729, 9.169236),
          zoom: 13,
          mapTypeId: google.maps.MapTypeId.ROADMAP,

          panControl: true,
          mapTypeControl: false,
          panControlOptions: {
              position: google.maps.ControlPosition.RIGHT_CENTER
          },
          zoomControl: true,
          zoomControlOptions: {
              style: google.maps.ZoomControlStyle.LARGE,
              position: google.maps.ControlPosition.RIGHT_CENTER
          },
          scaleControl: false,
          streetViewControl: false,
          streetViewControlOptions: {
              position: google.maps.ControlPosition.RIGHT_CENTER
          }
      };
      var map = new google.maps.Map(document.getElementById("mapCanvasCrise"), myOptions);

      var bounds = new google.maps.LatLngBounds();
      for (var i in avisos) {
        //var Item_1 = new google.maps.LatLng(avisos[i].avs_longitude, avisos[i].avs_latitude);
        var Item_1 = new google.maps.LatLng(avisos[i].avs_latitude, avisos[i].avs_longitude);
        var marker = new google.maps.Marker({
          position: Item_1,
          map: map
        });
        bounds.extend(Item_1);
      }
//      var myPlace = new google.maps.LatLng(45.4555729, 9.169236);
      /*var marker = new google.maps.Marker({
          position: myPlace,
          map: map
      });*/

      //bounds.extend(myPlace);
      map.fitBounds(bounds);

      var listener = google.maps.event.addListener(map, "idle", function() {
        if (map.getZoom() > 16) map.setZoom(10);
        google.maps.event.removeListener(listener);
      });

/*
      var triangleCoords = [];

      var array_regiao = regiao.split(',');

      var dois = 1;
      for (var ii = 0; ii < array_regiao.length; ii++) {
        if(dois == 2){
          dois = 0;
          var lng = array_regiao[ii].trim();
          if(lng > 0){
            lng = lng * -1;
          }
          triangleCoords.push({lat: lng, lng: lat});
        }
        dois++;
        var lat = array_regiao[ii].trim();
        if(lat > 0){
          lat = lat * -1;
        }
      }
      // Construct the polygon.
     var polygonRegiao = new google.maps.Polygon({
       paths: triangleCoords,
       strokeColor: '#FF0000',
       strokeOpacity: 0.8,
       strokeWeight: 2,
       fillColor: '#FF0000',
       fillOpacity: 0.35
     });
     polygonRegiao.setMap(map);*/


    }
    function loadCrisis(){
      $http.get('/rest/crisis/detail/'+vm.cri_id).success(function(data){
        vm.form = data;
        initialize(vm.form.avisos, vm.form.cri_regiao);
      });
    }

    UtilService.setCurrentMenu($scope, $location);
    loadCrisis();
    vm.goBack = function(){
      $location.path('/crisis');
    };

}]);
