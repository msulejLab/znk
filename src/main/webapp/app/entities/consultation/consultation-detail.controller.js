(function() {
    'use strict';

    angular
        .module('znkApp')
        .controller('ConsultationDetailController', ConsultationDetailController);

    ConsultationDetailController.$inject = ['$scope', '$rootScope', '$http', '$stateParams', 'previousState', 'entity', 'Consultation', 'User', 'Principal'];

    function ConsultationDetailController($scope, $rootScope, $http, $stateParams, previousState, entity, Consultation, User, Principal) {
        var vm = this;

        vm.consultation = entity;
        vm.previousState = previousState.name;
        vm.account = null;
        vm.error = null;
        vm.success = null;

        vm.bookIn = bookIn;
        vm.bookOut = bookOut;
        vm.isBookedIn = isBookedIn;

        var unsubscribe = $rootScope.$on('znkApp:consultationUpdate', function(event, result) {
            vm.consultation = result;
        });
        $scope.$on('$destroy', unsubscribe);

        Principal.identity().then(function (account) {
            vm.account = account;
        });

        function bookIn() {
            $http.post('/api/consultations/' + vm.consultation.id + '/book/' + vm.account.id)
                .then(function (response) {
                    vm.consultation = response.data;
                    vm.success = 'You have booked in to consultations';
                })
                .catch(function (response) {
                    vm.error = 'Error while trying to book in for consultations';
                    console.log(vm.error);
            });
        }

        function bookOut() {
            $http.post('/api/consultations/' + vm.consultation.id + '/unBook/' + vm.account.id)
                .then(function (response) {
                    vm.consultation = response.data;
                    vm.success = 'You have booked out from consultations';
                })
                .catch(function (response) {
                    vm.error = 'Error while trying to book out from consultations';
                    console.log(vm.error);
            })
        }

        function isBookedIn() {
            for (var i = 0; i <  vm.consultation.registeredStudents.length; i++) {
                if (vm.consultation.registeredStudents[i].login === vm.account.login) {
                    return true;
                }
            }

            return false;
        }
    }
})();
