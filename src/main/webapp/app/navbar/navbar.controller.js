(function() {
    'use strict';

    angular
        .module('znk')
        .controller('NavbarController', NavbarController);

    NavbarController.$inject = ['$rootScope', '$scope', '$state', 'User'];

    function NavbarController ($rootScope, $scope, $state, User) {
        $scope.logout = logout;
        $scope.isAuthenticated = isAuthenticated;

        $scope.$state = $state;

        loadIfSaveInStorage();

        function loadIfSaveInStorage() {
            var loggedUserJSON = sessionStorage.getItem('user');
            if (loggedUserJSON != null) {
                $rootScope.user = JSON.parse(loggedUserJSON);
            }
        }

        function logout() {
            console.log('User ' + $rootScope.user.login + ' logged out');
            $rootScope.user = null;
            sessionStorage.removeItem('user');
            localStorage.removeItem('user');
        }

        function isAuthenticated() {
            return $rootScope.user != null;
        }
    }
})();