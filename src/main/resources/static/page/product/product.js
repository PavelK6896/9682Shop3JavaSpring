app.controller('productCtrl', function ($scope, $http, $window, $location, factory, $rootScope) {

    $scope.getListProduct = () => {
        factory.getListProduct($scope, $http, $rootScope)
    }


    $scope.getListProductFilter = (filter, dd ) => {
        console.log(dd)
        $http({
            method: 'POST',
            url: 'http://localhost:8080/api/v1/products/filter',
            headers: {
                'Content-Type': 'application/json'
            },

            data: dd.dd

        }).then((response) => {
            console.log(response.data)
            $scope.products = response.data.content;

        }, (response) => {
            console.log('error', response);
        });


    }

    $rootScope.$on("getListProductFilter", function (filter, dd) { //export
        $scope.getListProductFilter(filter, dd );
    });


    $scope.getListProduct()


});

