/*global app*/
app.run(['$rootScope','$location', '$localStorage', '$http', 'toastr', 'UtilService',
    function ($rootScope, $location, $localStorage, $http, toastr, UtilService) {
'use strict';
        function validateRequest(next){
            if(next.requireLogin === true){
                toastr.error('Você não possui sessão ativa no sistema');
                $location.path('/index');
            }
        }

        $rootScope.$on('$routeChangeStart', function (event, next) {
                    if($localStorage.user !== undefined &&
                        $localStorage.user.hasOwnProperty('id') &&
                        $localStorage.user.id !== undefined){
                            $http.get('/rest/users/validation/' + $localStorage.user.id)
                            .then(function succes(response) {
                              if(response.data === false){
                                  UtilService.clearStorage();
                                  validateRequest(next);
                              }
                          });
                  }else{
                      UtilService.clearStorage();
                      validateRequest(next);
                  }
            $rootScope.$broadcast('headerMenuBroadcast');
        });
    }]);
