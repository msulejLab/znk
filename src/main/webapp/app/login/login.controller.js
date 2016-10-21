(function() {
    'use strict';

    angular
        .module('znk')
        .controller('LoginController', LoginController);

    LoginController.$inject = ['$rootScope', '$scope', '$http', '$state', 'User'];

    function LoginController ($rootScope, $scope, $http, $state, User) {
        $scope.login = login;

        $scope.authenticationError = false;
        $scope.authenticationSuccess = false;

        $scope.credentials = {
            login: '',
            password: '',
            rememberMe: false
        };

        function login () {
            $http({
                url: '/authenticate',
                method: "POST",
                data: $scope.credentials
            })
                .then(function (response) {
                    console.log(response);
                    $rootScope.user = response.data;
                    var userJSON = JSON.stringify(response.data);

                    if ($scope.credentials.rememberMe) {
                        localStorage.setItem('user', userJSON);
                    } else {
                        sessionStorage.setItem('user', userJSON);
                    }

                    console.log('Logged in: ' + $rootScope.user.login);
                    $state.go('home');
                },
                function (response) {
                    console.log('Error while logging in');

                    console.log(response);

                    if (response.status == 403) {
                        $scope.authenticationError = true;
                    }
                });
        }
    }
})();