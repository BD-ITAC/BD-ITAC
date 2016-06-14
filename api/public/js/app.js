var app = angular.module('bditac', ['ui.bootstrap',
       'bootstrapLightbox',
       'ngRoute', 'ngStorage','toastr','angularModalService',
       'checklist-model',
       'ngMap',
     'angular.filter']);

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
        .when('/avisos', {
            templateUrl: 'part-views/avisos.html',
            controller: 'AvisosController',
            requireLogin: true
        })
        .when('/crisis-type', {
            templateUrl: 'part-views/crisis-type.html',
            controller: 'CrisisTypeController',
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
        .when('/evento', {
            templateUrl: 'part-views/evento.html',
            controller: 'EventoController',
            requireLogin: true
        })
        .when('/sensors', {
            templateUrl: 'part-views/sensors.html',
            controller: 'SensorsController',
            requireLogin: true
        })
/*        .when('/situation-room', {
            templateUrl: 'views/situation-room.html',
            controller: 'SituationRoomController',
            requireLogin: true
        })*/
        .otherwise ({ redirectTo: '/dash-board' });
}]);
