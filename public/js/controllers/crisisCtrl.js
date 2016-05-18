'use strict';

app.controller('CrisisController', ['$scope', '$rootScope', '$location', '$http','$localStorage','toastr','UtilService',
 function($scope,$rootScope,$location,$http,$localStorage, toastr, UtilService) {
    var vm = this;
    vm.form = {submitted : false};

    $http.get('/rest/crisis').success(function(data){
debugger;
                $scope.CrisisListCollection = data;
        })


}]);
