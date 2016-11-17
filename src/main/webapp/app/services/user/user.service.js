(function () {
    'use strict';

    angular
        .module('znkApp')
        .factory('User', User);

    User.$inject = ['$resource', 'Principal'];

    function User ($resource, Principal) {
        var service = $resource('api/users/:login', {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'save': { method:'POST' },
            'update': { method:'PUT' },
            'delete':{ method:'DELETE'}
        });
        service.currentUserId = function(callback) {
            Principal.identity().then(function(account) {
                var temp = account;
                service.query(function(users){
                    for (var i = 0; i < users.length; i++) {
                        console.log(users[i].login);
                        if (users[i].login === temp.login) {
                            callback(users[i].id);
                            break;
                        }
                    }

                })
            });
        };
        return service;
    }
})();
