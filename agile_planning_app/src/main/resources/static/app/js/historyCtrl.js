var HistoryController = function($scope, $http) {
    console.log("history controller");
    	$scope.visibility={}
    	
    	$scope.visibility.planning = false;
        var gett = $http({
            url: "/sprints",
            method: "GET",
        });
          
        gett.success(function(data, status) {
            console.log("success");
             $scope.sprints = data;
        });
        
        gett.error(function(data, status) {
             console.log("error");
        });
        
        
        $scope.sprintSelectChanged = function(){
        	if ($scope.selectedSprint != null){
        		
        		var gett = $http({
                    url: "/get/planning",
                    method: "GET",
                    params: {"sprintnr": $scope.selectedSprint}
                });
                  
                gett.success(function(data, status) {
                	 console.log("success");
                     $scope.plng = data;
                     $scope.plng.sdate = new Date(data.sdate);
                     $scope.plng.edate = new Date(data.edate);
                     for (var i = 0; i < $scope.plng.rows.length; i++){
                    	 var row = $scope.plng.rows[i];
                    	 for (var j = 3; j < row.length; j++){
                    		 $scope.plng.rows[i][j] = parseFloat(row[j])
                         }
                     }
                     $scope.visibility.planning = true;
                     $scope.visibility.showAllocationTable=true;
                });
                
                gett.error(function(data, status) {
                     console.log("error");
                });
        	} else{
        		$scope.visibility.planning = false;
        		$scope.visibility.showAllocationTable=false;
        	}
        }
       
    
  };