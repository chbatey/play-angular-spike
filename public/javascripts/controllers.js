'use strict';

/* Controllers */

var stationControllers = angular.module('stationControllers', []);

stationControllers.controller('WeatherStationListCtrl', ['$scope', '$http',
    function($scope, $http) {
        $http.get('/stations').success(function(data) {
            console.info(data)
            $scope.weather_stations = data
        });
    }]);

stationControllers.controller('WeatherStationDataCtrl', ['$scope', '$websocket',
    function($scope, $websocket) {

        $scope.liveData = "Unknown"
        var ws = $websocket.$new('ws://localhost:9000/socket'); // instance of ngWebsocket, handled by $websocket service

        ws.$on('$open', function () {
            console.log('Oh my gosh, websocket is really open! Fukken awesome!');
        });

        ws.$on('pong', function (data) {
            console.log('The websocket server has sent the following data:');
            console.log(data);
            $scope.$apply(function() {
                $scope.liveData = data
            });
        });

        ws.$on('$close', function () {
            console.log('Noooooooooou, I want to have more fun with ngWebsocket, damn it!');
        });

    }]);

stationControllers.controller('WeatherStationDetailCtrl', ['$scope', '$routeParams',
    function($scope, $routeParams) {
        $scope.stationId = $routeParams.stationId
    }]);




