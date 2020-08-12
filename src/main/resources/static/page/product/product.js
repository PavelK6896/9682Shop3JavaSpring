

app.controller('productCtrl', function ($scope, $http, $window, $location) {

    $scope.getListProduct = () => {
        $http({
            method: 'GET',
            url: 'http://localhost:8080/api/v1/products/list'
        }).then((response) => {
            $scope.products =  response.data;
            $scope.size =  response.data.length;
            $scope.product = response.data[0];
        }, (response) =>  {
            console.log('error', response);
        });
    }
    $scope.addProduct = () => {
        $http({
            method: 'POST',
            url: 'http://localhost:8080/api/v1/products',
            headers: {
                'Content-Type': 'application/json'
            },
            data: $scope.product
        }).then( (response) =>  {
            $scope.products.push(response.data);
        }, (response) => {
            console.log(response)
        });
    }

    $scope.deleteProduct = (id) => {
        $http({
            method: 'DELETE',
            url: 'http://localhost:8080/api/v1/products/' + id,
        }).then((response) => {
            $scope.getListProduct();
        },(response) => {
            console.log('error', response);
        });
    }

    $scope.update = (product) => {
        $scope.product = product
    }
});



