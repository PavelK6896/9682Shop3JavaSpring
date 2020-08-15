

app.controller('profileCtrl', function ($scope, $window, $http) {
    console.log('profileCtrl');


    if ($window.sessionStorage.userData !== 'undefined' ){
        if ($window.sessionStorage.userData !== undefined){
            const userData = JSON.parse($window.sessionStorage.userData)
            $scope.name = 'Name ' + userData.userName
            $scope.role = userData.role
        }
    }
});
