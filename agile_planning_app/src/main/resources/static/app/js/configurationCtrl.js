var ConfigController = function($scope, $http) {

	  var gett = $http({
          url: "/get/configurations",
          method: "GET",
      });
        
      gett.success(function(data, status) {
          console.log("success");
          $scope.constants = data;
      });
      
      gett.error(function(data, status) {
           console.log("error");
      });
      
      $scope.saveConfig = function(){
    	  var post = $http.post("/save/configurations", $scope.constants);
          
          post.success(function(data, status) {
                console.log( "success message: " + JSON.stringify({data: data}));
          });
          
          post.error(function(data, status) {
          	 console.log( "failure message: " + JSON.stringify({data: data}));
          });
      }
	  
  }