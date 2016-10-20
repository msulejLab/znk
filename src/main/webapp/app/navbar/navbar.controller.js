(function() {
    'use strict';

    angular
        .module('znk')
        .controller('NavbarController', NavbarController);

    NavbarController.$inject = ['$scope', '$state', 'User'];

    function NavbarController ($scope, $state, User) {
        $scope.login = login;
        $scope.logout = logout;
        $scope.isAuthenticated = isAuthenticated;

        $scope.$state = $state;
        $scope.user = null;
        $scope.boards = null;

        function login() {
            User.get({id : "1"}, onSuccess, onError);

            function onSuccess(data) {
                console.log('User ' + data.login + ' logged in');
                $scope.user = data;
                localStorage.setItem('user', data);
            }

            function onError() {
                console.log('Error while loading user');
            }
        }

        function logout() {
            console.log('User ' + $scope.user.login + ' logged out');
            $scope.user = null;
            localStorage.removeItem('user');
        }

        function isAuthenticated() {
            return $scope.user != null;
        }
    }
})();