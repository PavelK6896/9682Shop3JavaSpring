


app.controller('addProductCtrl', function ($scope, $http, $window, $location, factory, $rootScope) {


    $scope.getListProduct = () => {
        factory.getListProduct($scope, $http, $rootScope)
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
            if (response.status === 204){
                $scope.info = 'это не продукт '
                return
            }

            if (response.status === 208){
                $scope.info = 'уже создан продукт '
                return
            }

            $scope.products.push(response.data);
            $scope.info = 'продукт добавлен ' + response.data.title

        }, (response) => {
            $scope.info = 'продукт не добавлен ' + response.status + ' не авторизован'
            console.log(response)
        });
    }



    $scope.updateProduct = () => {
        console.log($scope.product)
        $http({
            method: 'PUT',
            url: 'http://localhost:8080/api/v1/products',
            headers: {
                'Content-Type': 'application/json'
            },
            data: $scope.product
        }).then((response) => {
            if (response.status === 204){
                $scope.info = 'это не продукт '
                return
            }
            $scope.info = 'продукт c id ' + response.data.id + ' обнавлен'
            console.log(response)

        }, (response) => {
            if (response.status === 404){
                $scope.info = 'продукт c id ' + response.data.id + ' не найден'
            }
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
