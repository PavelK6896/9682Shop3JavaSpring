


app.controller('addProductCtrl', function ($scope, $http, $window, $location, factory) {


    $scope.getListProduct = () => {
        factory.getListProduct($scope, $http)
    }

    $scope.getListProduct()

    $scope.addProduct = () => {

        let hasError = false

        if (!$scope.product) {
            $scope.amountError = 'error';
            return;
        }

        if (isNaN($scope.product.id)) {
            $scope.amountError = 'error';
            hasError = true
        } else {
            $scope.amountError = '';
        }
        if (hasError) return

        $http({
            method: 'POST',
            url: 'http://localhost:8080/api/v1/products',
            headers: {
                'Content-Type': 'application/json'
            },
            data: $scope.product
        }).then((response) => {
            $scope.products.push(response.data);
            $scope.info = 'продукт добавлен ' + response.data.title
        }, (response) => {
            $scope.info = 'продукт не добавлен ' + response.status + ' не авторизован'
            console.log(response)
        });
    }

    $scope.deleteProduct = (id) => {
        $http({
            method: 'DELETE',
            url: 'http://localhost:8080/api/v1/products/' + id,
        }).then((response) => {
            $scope.getListProduct();
            $scope.info = 'продукт удален id ' + + id
        }, (response) => {
            console.log('error', response);
            $scope.info = 'продукт не удален ' +  response.status + ' не авторизован'
        });
    }

    $scope.update = (product) => {
        $scope.product = product
        $scope.info = 'редоктировать продукт ' + product.title + ' id ' + + product.id
    }

})
