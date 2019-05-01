(function(angular) {
  angular.module("myApp.controllers", []);
  angular.module("myApp.services", []);
  var app = angular.module("myApp", ["ngResource", "ngRoute", "myApp.controllers", "myApp.services"]);
  
  app.config(['$routeProvider', 
        function($routeProvider) {
            $routeProvider.when('/planning', {
                templateUrl: 'app/views/planning.html',
                controller: 'PlanninngCtrl'
            }).when('/history', {
                templateUrl: 'app/views/history.html',
                controller: 'HistoryCtrl'
            }).when('/config', {
                templateUrl: 'app/views/config.html',
                controller: 'ConfigCtrl'
            }).otherwise({redirectTo: '/planning'});
        }]);
  
}(angular));
