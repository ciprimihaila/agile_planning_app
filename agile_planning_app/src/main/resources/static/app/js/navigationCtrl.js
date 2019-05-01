 var NavigationController = function($scope, $location) {
    console.log("NavCtrl");
    $scope.isActive = function (viewLocation) { 
        return viewLocation === $location.path();
    };
  };