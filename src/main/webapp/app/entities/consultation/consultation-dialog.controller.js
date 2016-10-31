(function() {
    'use strict';

    angular
        .module('znkApp')
        .controller('ConsultationDialogController', ConsultationDialogController);

    ConsultationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Consultation', 'User'];

    function ConsultationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Consultation, User) {
        var vm = this;

        vm.consultation = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.consultation.id !== null) {
                Consultation.update(vm.consultation, onSaveSuccess, onSaveError);
            } else {
                Consultation.save(vm.consultation, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('znkApp:consultationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dateTime = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
