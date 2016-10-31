(function() {
    'use strict';

    angular
        .module('znkApp')
        .controller('ConsultationDetailController', ConsultationDetailController);

    ConsultationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Consultation', 'User'];

    function ConsultationDetailController($scope, $rootScope, $stateParams, previousState, entity, Consultation, User) {
        var vm = this;

        vm.consultation = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('znkApp:consultationUpdate', function(event, result) {
            vm.consultation = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
