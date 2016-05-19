var app = angular.module('bditac', ['ngRoute', 'ngStorage','toastr']);

app.config(['$routeProvider','$httpProvider',
function ($routeProvider, $httpProvider) {

  $routeProvider
        .when('/index', {
            templateUrl: 'part-views/login.html',
            controller: 'LoginController',
            requireLogin: false
        })
        .when('/dash-board', {
            templateUrl: 'part-views/dash-board.html',
            controller: 'DashBoardController',
            requireLogin: true
        })
        .when('/crisis', {
            templateUrl: 'part-views/crisis.html',
            controller: 'CrisisController',
            requireLogin: false
        })
/*        .when('/situation-room', {
            templateUrl: 'views/situation-room.html',
            controller: 'SituationRoomController',
            requireLogin: true
        })*/
        .otherwise ({ redirectTo: '/dash-board' });
}]);
