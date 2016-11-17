(function () {
    'use strict';

    angular
        .module('znkApp')
        .controller('ConsultationDialogController', ConsultationDialogController);

    ConsultationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Consultation', 'User', 'Principal'];

    function ConsultationDialogController($timeout, $scope, $stateParams, $uibModalInstance, entity, Consultation, User, Principal) {
        var vm = this;

        vm.consultation = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.users = [];
        vm.currentUserId = -1;

        User.query({}, onSuccess);

        function onSuccess(data, headers) {
            //hide anonymous user from user management: it's a required user for Spring Security
            for (var i in data) {
                if (data[i]['login'] === 'anonymoususer') {
                    data.splice(i, 1);
                }
            }
            vm.users = data;
            currentUser();
        }

        $timeout(function () {
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function save() {
            vm.isSaving = true;
            vm.consultation.cancelled = false;
            vm.consultation.teacherId = vm.currentUserId;
            if (vm.consultation.id !== null) {
                Consultation.update(vm.consultation, onSaveSuccess, onSaveError);
            } else {
                console.log(vm.consultation);
                Consultation.save(vm.consultation, onSaveSuccess, onSaveError);
            }
        }

        function currentUser() {
            Principal.identity().then(function(account) {
                vm.account = account;
                for (var i = 0; i < vm.users.length; i++) {
                    console.log(vm.users[i].login);
                    if (vm.users[i].login === vm.account.login) {
                        vm.currentUserId = vm.users[i].id;
                        break;
                    }
                }
            });
        }

        function onSaveSuccess(result) {
            $scope.$emit('znkApp:consultationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError() {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dateTime = false;

        function openCalendar(date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
