(function(angular) {
	angular.module("myApp.services").service('loadConstants', function($http) {
		this.getConstants = function(){
			
			var gett = $http({
		        url: "/get/configurations",
		        method: "GET",
		    });
		      
		    gett.success(function(data, status) {
		        console.log("success");
		    });
		    
		    gett.error(function(data, status) {
		         console.log("error");
		    });
		    return gett;
		}
	});
}(angular));