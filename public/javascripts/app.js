'use strict';

/* App Module */

var weatherApp = angular.module('weatherApp', [
    'ngWebsocket',
    'ngTable',
    'ngRoute',
    'stationControllers'
]);

weatherApp.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.
            when('/stations', {
                templateUrl: 'station-list.html',
                controller: 'WeatherStationListCtrl'
            }).
            when('/stations/:stationId', {
                templateUrl: 'station-detail.html',
                controller: 'WeatherStationDetailCtrl'
            }).
            when('/data', {
                templateUrl: 'live-data.html',
                controller: 'WeatherStationDataCtrl'
            }).
            otherwise({
                redirectTo: '/stations'
            });
    }]);

