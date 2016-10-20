(function() {
    'use strict';

    angular
        .module('znk')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$rootScope', '$scope', '$state', 'User'];

    function HomeController ($rootScope, $scope, $state, User) {
        $scope.$state = $state;
        $scope.appMsg = 'Hello from controller!'
    }
})();