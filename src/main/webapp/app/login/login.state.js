(function() {
    'use strict';

    angular
        .module('znk')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('login', {
            parent: 'app',
            url: '/login',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/login/login.html',
                    controller: 'LoginController',
                    controllerAs: 'LoginCtrl'
                }
            }
        });
    }
})();