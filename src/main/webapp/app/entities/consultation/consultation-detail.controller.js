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
        vm.teacher = {};

        loadTeacher();

        function loadTeacher() {
            vm.teacher = User.get({login:entity.teacherLogin}, function (response) {
                vm.teacher = response;
            })
        }

        vm.bookIn = bookIn;
        vm.bookOut = bookOut;
        vm.isBookedIn = isBookedIn;
        vm.makeCancel = makeCancel;
        vm.getName = getName;

        var unsubscribe = $rootScope.$on('znkApp:consultationUpdate', function(event, result) {
            vm.consultation = result;
        });
        $scope.$on('$destroy', unsubscribe);

        Principal.identity().then(function (account) {
            vm.account = account;
        });

        function bookIn() {
            $http.post('/api/consultations/' + vm.consultation.id + '/book/')
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
            $http.post('/api/consultations/' + vm.consultation.id + '/unBook/')
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

        function makeCancel() {
            console.log('idzie cancel');
            $http.post('/api/consultations/' + vm.consultation.id + '/cancel')
                .then(function (response) {
                    vm.consultation.cancelled = true;
                    vm.success = 'You cancelled the consultation';
                })
                .catch(function (rsponse) {
                    vm.error = 'Error while trying to cancel consultation';
                    console.log(vm.error);
                })
        }

        function getName(person) {
            if (person.firstName === null || person.firstName === undefined || person.lastName === null || person.lastName === undefined) {
                return person.login;
            } else {
                return person.firstName + ' ' + person.lastName;
            }
        }
    }
})();
