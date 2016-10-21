(function() {
    'use strict';

    angular
        .module('znk')
        .factory('LoginService', LoginService);

    LoginService.$inject = ['$http', '$localStorage', '$sessionStorage', '$q'];

    function LoginService ($http, $q) {
        var service = {
            login: login,
            logout: logout
        };

        return service;

        function login (credentials) {
            var data = {
                login: credentials.login,
                password: credentials.password,
                rememberMe: credentials.rememberMe
            };

            return $http.post('authenticate', data).success(
                authenticateSuccess
            );
        }

        function logout () {
            delete sessionStorage.removeItem('user');
            delete localStorage.removeItem('user');
        }
    }
})();