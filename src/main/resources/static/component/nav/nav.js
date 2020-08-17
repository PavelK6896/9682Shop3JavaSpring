
app.directive('navBar', () => {
    return {
        restrict: 'E',
        transclude: true,
        templateUrl: 'component/nav/nav.html',
        link: (scope, element, attrs, ctrl, transclude) => {
            transclude(scope, (clone, scope) => {
                element.append(clone);
            });
        }
    }
});


app.controller('navBarCtrl',  ($scope, $http, $window, factory) => {
    console.log('navBarCtrl')

    $scope.text1 = ""
    $scope.admin = false

    if ($window.sessionStorage.userData !== 'undefined' ){
        if ($window.sessionStorage.userData !== undefined){
            const userData = JSON.parse($window.sessionStorage.userData)
            $scope.name = 'Name ' + userData.userName
            $http.defaults.headers.common['Authorization'] = 'Basic ' + userData.token;
            $scope.text1 = "Authentication ok"
            $scope.btn1 = true;
            if (userData.role === 'ROLE_ADMIN'){
                $scope.admin = true
            }
        }
    }

    $scope.logout = () => {
        console.log("logout" )
        $http.defaults.headers.common['Authorization'] = "";
        $scope.btn1 = false
        $scope.admin = false
        $scope.name = ''
        $scope.text1 = ''
        $window.sessionStorage.userData = undefined
    }

    $scope.login = () => {
        console.log("login")
        let token = $window.btoa($scope.username + ':' + $scope.password);
        let userData = {
            userName: $scope.username,
            token,
            role: ''
        }
        $scope.name = 'Name ' + $scope.username
        $scope.username = ''
        $scope.password = ''

        $http.defaults.headers.common['Authorization'] = 'Basic ' + token;

        $http({
            method: 'GET',
            url: 'http://localhost:8080/profile/api/v1/authorization'
        }).then((response) => {
            console.log('login success', response);
            $scope.text1 = "Authentication ok"
            $scope.btn1 = true;
            response.data.map(v => { // если админ
                if (v.authority === 'ROLE_ADMIN'){
                    userData.role = 'ROLE_ADMIN'
                    $scope.admin = true
                }
            })
            $window.sessionStorage.setItem('userData', JSON.stringify(userData));
        }, (response) => {
            console.log('error', response);
            $scope.text1 = "Authentication failed"
        });
        console.log($window)
        console.log(window)
        console.log($http.defaults)
    }
})

