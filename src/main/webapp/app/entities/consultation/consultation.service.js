(function() {
    'use strict';
    angular
        .module('znkApp')
        .factory('Consultation', Consultation);

    Consultation.$inject = ['$resource', 'DateUtils'];

    function Consultation ($resource, DateUtils) {
        var resourceUrl =  'api/consultations/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dateTime = data.dateTime;
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
