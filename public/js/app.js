var app = angular.module('bditac', ['ngRoute', 'ngStorage']);

app.config(['$routeProvider','$httpProvider',
function ($routeProvider, $httpProvider) {

  $routeProvider
        .when('/index', {
            templateUrl: 'part-views/login.html',
            controller: 'LoginController',
            requireLogin: false
        })
/*        .when('/situation-room', {
            templateUrl: 'views/situation-room.html',
            controller: 'SituationRoomController',
            requireLogin: true
        })*/
        .otherwise ({ redirectTo: '/index' });
}]);
