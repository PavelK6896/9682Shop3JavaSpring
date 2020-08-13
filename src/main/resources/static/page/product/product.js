app.controller('productCtrl', function ($scope, $http, $window, $location, factory) {

    $scope.getListProduct = () => {
        factory.getListProduct($scope, $http)
    }

    $scope.getListProduct()


});

app.factory('factory', [() => {

    const onAuth = () => {

        return "000000";
    }
    this.text99 = "999"

    const getListProduct = ($scope, $http) => {

        $http({
            method: 'GET',
            url: 'http://localhost:8080/api/v1/products/list'
        }).then((response) => {
            $scope.info = 'все продукты '
            $scope.products = response.data;
            $scope.size = response.data.length;
            $scope.product = response.data[0];
        }, (response) => {
            $scope.info = 'сервер недоступен '
            console.log('error', response);
        });
    }

    return {
        onAuth,
        text99,
        getListProduct
    };
}]);



/////////////////////////////////
app.controller('One', ['$scope', '$rootScope',
    function($scope) {
        $rootScope.$on("CallParentMethod", function(){ //export
            $scope.parentmethod();
        });


        $scope.parentmethod = function() {
            console.log("sssssssssssssssss")
            // task
        }
    }
]);
app.controller('two', ['$scope', '$rootScope',    function($scope) {

    $scope.childmethod = function() {
        $rootScope.$emit("CallParentMethod", {}); //import
    }


}
]);
