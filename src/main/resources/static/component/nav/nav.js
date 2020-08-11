
app.directive('navBar', () => {
    console.log('directive navBar');
    return {
        restrict: 'E',
        transclude: true,
        templateUrl: 'component/nav/nav.html',
        link: (scope, element, attrs, ctrl, transclude) => {
            transclude(scope, (clone, scope) => {
                element.append(clone); // добовляем в конец клон
            });

        }
    }
});


app.controller('c1',  ($scope, $http, $window) => {

    $scope.text1 = ""
    if ($http.defaults.headers.common['Authorization'] === ""){
        $scope.btn1 = true
    }

    $scope.logout = () => {
        console.log("logout" )
        $http.defaults.headers.common['Authorization'] = "";
        //$scope.products = []
        $scope.btn1 = false;
    }

    $scope.login = () => {

        console.log("login")

        let token = $window.btoa($scope.username + ':' + $scope.password);

        let userData = {
            userName: $scope.username,
            authData: token
        }

        $scope.name = this.username = $scope.username
        $scope.username = ''
        $scope.password = ''

        $window.sessionStorage.setItem('userData', JSON.stringify(userData));
        $http.defaults.headers.common['Authorization'] = 'Basic ' + token;

        $http({
            method: 'GET',
            url: 'http://localhost:8080/api/v1/authorization'
        }).then((response) => {
            console.log('success', response);
            $scope.text1 = "Authentication ok"
            $scope.btn1 = true;
        }, (response) => {
            console.log('error', response);

            $scope.text1 = "Authentication failed"
        });

        // $scope.btn1 = true;
        console.log($http.defaults.headers.common['Authorization'])
        console.log($window.sessionStorage.userData)
    }
})

