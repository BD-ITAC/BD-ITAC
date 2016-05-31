var app = angular.module('bdITAC', ['ngRoute', 'ngAnimate', 'toastr']);

app.config(['$routeProvider', '$locationProvider', function($routeProvider, $locationProvider)
{
   // remove o # da url
   //$locationProvider.html5Mode(true);

   $routeProvider

   // para a rota '/', carregaremos o template home.html e o controller 'HomeCtrl'
   .when('/', {
      templateUrl : 'page/one',
      controller     : 'OneCtrl',
   })
   .when('/four', {
      templateUrl : 'part-views/four.html',
      controller     : 'FourCtrl',
   })

   // para a rota '/sobre', carregaremos o template sobre.html e o controller 'SobreCtrl'
   /*
   .when('/sobre', {
      templateUrl : 'app/views/sobre.html',
      controller  : 'SobreCtrl',
   })
   */
   // caso não seja nenhum desses, redirecione para a rota '/'
   .otherwise ({ redirectTo: '/' });
}]);
