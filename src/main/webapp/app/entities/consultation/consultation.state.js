(function() {
    'use strict';

    angular
        .module('znkApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('consultation', {
            parent: 'entity',
            url: '/consultation?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Consultations'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/consultation/consultations.html',
                    controller: 'ConsultationController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
            }
        })
        .state('consultation-detail', {
            parent: 'entity',
            url: '/consultation/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Consultation'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/consultation/consultation-detail.html',
                    controller: 'ConsultationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Consultation', function($stateParams, Consultation) {
                    return Consultation.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'consultation',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('consultation-detail.edit', {
            parent: 'consultation-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/consultation/consultation-dialog.html',
                    controller: 'ConsultationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Consultation', function(Consultation) {
                            return Consultation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('consultation.new', {
            parent: 'consultation',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/consultation/consultation-dialog.html',
                    controller: 'ConsultationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                dateTime: null,
                                cancelled: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('consultation', null, { reload: 'consultation' });
                }, function() {
                    $state.go('consultation');
                });
            }]
        })
        .state('consultation.edit', {
            parent: 'consultation',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/consultation/consultation-dialog.html',
                    controller: 'ConsultationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Consultation', function(Consultation) {
                            return Consultation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('consultation', null, { reload: 'consultation' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('consultation.delete', {
            parent: 'consultation',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/consultation/consultation-delete-dialog.html',
                    controller: 'ConsultationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Consultation', function(Consultation) {
                            return Consultation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('consultation', null, { reload: 'consultation' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
