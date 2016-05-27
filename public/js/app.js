var app = angular.module('bditac', ['ui.bootstrap','bootstrapLightbox','ngRoute', 'ngStorage','toastr']);

app.config(['$routeProvider','$httpProvider', '$compileProvider',
function ($routeProvider, $httpProvider, $compileProvider) {

  $compileProvider.aHrefSanitizationWhitelist(/^\s*(https?|file|ftp|blob):|data:image\//);


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
            requireLogin: true
        })
        .when('/classification', {
            templateUrl: 'part-views/classification.html',
            controller: 'ClassificationController',
            requireLogin: true
        })
/*        .when('/situation-room', {
            templateUrl: 'views/situation-room.html',
            controller: 'SituationRoomController',
            requireLogin: true
        })*/
        .otherwise ({ redirectTo: '/dash-board' });
}]);
