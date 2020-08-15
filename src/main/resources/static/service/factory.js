

app.factory('factory', [() => {

    const getListProduct = ($scope, $http, $rootScope) => {

        $http({
            method: 'GET',
            url: 'http://localhost:8080/api/v1/products/page'
        }).then((response) => {
            $scope.info = 'все продукты '
            $scope.products = response.data.content;
            $scope.size = response.data.totalElements;
            $scope.product = response.data.content[0];

            $rootScope.categories = response.data.content[0].categories
            this.cat = response.data.content[0].categories

            console.log(response.data)


        }, (response) => {
            $scope.info = 'сервер недоступен '
            console.log('error', response);
        });
    }

    return {
        getListProduct
    };
}]);
