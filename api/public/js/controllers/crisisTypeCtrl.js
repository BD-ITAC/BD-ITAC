/*
 * @author: Danilo Ramalho
 */

'use strict';

app.controller('CrisisTypeController', ['$scope', '$rootScope', '$location', '$http','$localStorage',
  'toastr','UtilService',
 function($scope,$rootScope,$location,$http,$localStorage, toastr, UtilService) {
    var vm = this;
    //vm.classList = [];
    vm.form = {};

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

    vm.clearForm = function(){
      vm.form.subtype = vm.form.type = vm.form.subgroup = vm.form.group = vm.form.class = undefined;
      vm.form.description = vm.form.cobrade = vm.form.definition = '';
    };

    vm.save = function(form){
      //vm.submitted = true;
      if(form.$invalid === true){
        toastr.info('Please complete all fields!');
      }else{
        var param = {
          ctc_id: vm.form.class,
          ctg_id: vm.form.group,
          cts_id: vm.form.subgroup,
          ctt_id: vm.form.type,
          ctp_id: vm.form.subtype,
          crt_descricao: vm.form.description,
          crt_definicao: vm.form.definition,
          crt_cobrade: vm.form.cobrade
        };
        $http.post('rest/crisis/type', param).then(function(res){
          if(res.status === 200){
            toastr.success('Type of crisis successfully saved!');
            vm.clearForm();
          }else{
            toast.error(res.data.message);
          }
        });
      }
    };

}]);
