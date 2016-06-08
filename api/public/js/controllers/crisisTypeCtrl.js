/*
 * @author: Danilo Ramalho
 */

'use strict';

app.controller('CrisisTypeController', ['$scope', '$rootScope', '$location', '$http','$localStorage',
  'toastr','UtilService',
 function($scope,$rootScope,$location,$http,$localStorage, toastr, UtilService) {
    var vm = this;
    //vm.classList = [];
    vm.form = {submitted : false};

    UtilService.setCurrentMenu($scope, $location);

    function loadClass(){
      $http.get('rest/crisis/type/class').then(function (res){
        vm.classList = res.data;
      });
    }

    angular.element(document).ready(function () {
      loadClass();
    });

    vm.chooseClass = function(){
      $http.get('rest/crisis/type/group/'+vm.form.class).then(function (res){
        vm.groupList = res.data;
      });
    };

    vm.chooseGroup = function(){
      $http.get('rest/crisis/type/subgroup/'+vm.form.class+'/'+vm.form.group).then(function (res){
        vm.subGroupList = res.data;
      });
    };

    vm.chooseSubGroup = function(){
      $http.get('rest/crisis/type/type/'+vm.form.class+'/'+vm.form.group+'/'+vm.form.subgroup).then(function (res){
        vm.typeList = res.data;
      });
    };

    vm.chooseType = function(){
      $http.get('rest/crisis/type/subtype/'+vm.form.class+'/'+vm.form.group+'/'+vm.form.subgroup+'/'+vm.form.type).then(function (res){
        vm.subTypeList = res.data;
      });
    };


}]);
