function initDates(sprintLength){
	var dates = {};
	
    var today = new Date(); //todo format date 
    var startDate = new Date(); 
    var sday = today.getDate() + 1;
    startDate.setDate(sday);
    
    var endDate  = new Date();
    endDate.setDate(sday + (sprintLength - 1)); 
    
    dates.sdate = startDate;
    dates.edate = endDate;
	return dates;
}

function filterWeekends(dates){
	var result = [];
	for (var i = 0; i < dates.length; i ++){
		if (!isWeekendDay(dates[i])){
			result.push(dates[i]);
		}
	}
	return result;
}

function isWeekendDay(date){
	return date.getDay() == 6 || date.getDay() == 0;
}

function calculateDates (sdate, edate) {
	
	var oneDay = 24*60*60*1000; // hours*minutes*seconds*milliseconds
	var dayNo = Math.round(Math.abs((sdate.getTime() -  edate.getTime())/(oneDay)));
	
	var dates = [];
	
	for (var i = 0; i < dayNo + 1; i ++){
		nextDate = new Date(sdate.getTime() + oneDay * i);
		dates.push(nextDate);
	}
	return filterWeekends(dates);

}

function computeTableColumns(devs){
	var init_cols = ['Index', 'Date', 'Week Day', 'Day Default'];//todo index !!!
    var dev_cols = devs.split(",");
    return init_cols.concat(dev_cols);
}

function getWeekDay(date){
	if (typeof date !== 'undefined'){
		switch(date.getDay()){
			case 0: return "SUN";
			case 1: return "MON";
			case 2: return "TUE";
			case 3: return "WED";
			case 4: return "THU";
			case 5: return "FRI";
			case 6: return "SAT";
		}
	}
}


function completeAllocationTable(cols, allocDates, constants){
	var result = [];
	for (var j = 0; j < allocDates.length; j++){
    	var row = [];
    	for (var i = 0; i < cols.length; i++){
        	if (cols[i] == 'Index'){
        		row[i] = j + 1;
        	} else if (cols[i] == 'Date'){
        		row[i] = allocDates[j]; //format in interfata
        	} else if (cols[i] == 'Week Day'){
        		row[i] = getWeekDay(allocDates[j]);
        	} else {
        		if (j == allocDates.length - 1){ //review, retrospective, planning - last day of sprint
        			row[i] = parseFloat(constants.dayReview);
        		} else{
        			row[i] = parseFloat(constants.dayAlloc);
        		}
        	}
        }
    	result[j] = row;
    }
	return result;
}

(function(angular) {
  var PlanningController = function($scope, $http, loadConstants) {
	  
    loadConstants.getConstants().then(function(result){
    	console.log("planning controller");
        
        $scope.constants = {}
        $scope.plng = {}
        
        $scope.range = function(min, max, step){
            step = step || 1;
            var input = [];
            for (var i = min; i <= max; i += step) 
            	input.push(i.toFixed(1));
            return input;
         };
         
        $scope.dayDefaultSelected = "0.9";
        $scope.setDayDefault = function(value, row){
        	for (var i = 3; i < row.length; i++)
        		row[i] = parseFloat(value);
        }
        
        $scope.plng.velocity = result.data[0].value; //0
        $scope.constants.dayAlloc = result.data[1].value; //1
        $scope.constants.dayReview = result.data[2].value; //2
        $scope.constants.spPerDay = result.data[3].value; //3
        $scope.constants.sprintLength = result.data[4].value;//4
        $scope.plng.devs = result.data[5].value;//5
        $scope.plng.sprintnr = result.data[6].value;//6
        
        var dates = initDates($scope.constants.sprintLength);
        $scope.plng.sdate = dates.sdate;
        $scope.plng.edate = dates.edate;
   	 	
        $scope.visibility = {};
        $scope.visibility.showAllocationTable = false;
        $scope.visibility.showComputeAllocation = false;
        
        $scope.getInitialTableAllocation = function () {
    		$scope.allocDates = calculateDates($scope.plng.sdate, $scope.plng.edate);
    		$scope.plng.cols = computeTableColumns($scope.plng.devs);
    		$scope.plng.rows = completeAllocationTable($scope.plng.cols, $scope.allocDates, $scope.constants);
    		$scope.computeAllocation();
    		$scope.visibility.showAllocationTable = true;
        };
    })
           
    $scope.computeAllocation = function(){
    	var allAlloc = 0;
    	for (var i = 0; i < $scope.plng.rows.length; i++){
    		for (var j = 3; j < $scope.plng.cols.length; j++){
    			allAlloc = allAlloc + $scope.plng.rows[i][j] * $scope.constants.spPerDay;
    		}
    	}
    	$scope.plng.wdays = allAlloc / 10;
    	$scope.plng.spoints = allAlloc * $scope.plng.velocity;
    	$scope.visibility.showComputeAllocation = true;
    	
    	$scope.computeStoryPoints();
    	
    }
    
    $scope.computeStoryPoints = function(){
    	
    	if (typeof $scope.plng.spadded !== 'undefined'){
    		   		
	    	var storyArr = $scope.plng.spadded.split(",");
	    	var spAdded = 0;
	    	for (var i = 0; i < storyArr.length; i++){
	    		spAdded = spAdded + parseInt(storyArr[i]);
	    	}
	    	$scope.plng.spconsumed = spAdded;
	    	$scope.plng.spremain = $scope.plng.spoints - $scope.plng.spconsumed;
    	
    	} else{
    		$scope.plng.spremain = $scope.plng.spoints;
    		$scope.plng.spconsumed = 0;
    	}
    	
    	$scope.msg = {};
    	if (parseInt($scope.plng.spremain) >= 0){
    		$scope.msg.msgclass = 'alert alert-success';
    		$scope.msg.status = "Status ok"
    	} else {
    		$scope.msg.msgclass = 'alert alert-danger';
    		$scope.msg.status = "Excess allocation"
    	}
    }
    
    $scope.initInputChanged = function(){
    	$scope.visibility.showAllocationTable = false;
    } 
 
    function save() {
    	var post = $http.post("/save/planning", $scope.plng);
            
        post.success(function(data, status) {
              console.log( "success message: " + JSON.stringify({data: data}));
        });
        
        post.error(function(data, status) {
        	 console.log( "failure message: " + JSON.stringify({data: data}));
        });
        
    };
  
    $scope.save = save;
    
     
  }
  
  angular.module("myApp.controllers").controller("PlanninngCtrl", PlanningController);
  angular.module("myApp.controllers").controller("HistoryCtrl", HistoryController);
  angular.module("myApp.controllers").controller("NavigCtrl", NavigationController);
  angular.module("myApp.controllers").controller("ConfigCtrl", ConfigController);

}(angular));
