var app = angular.module('app', ['ngRoute']);

app.config(function ($routeProvider) {

    console.log("ssss")
    $routeProvider
        .when('/', {
            templateUrl: 'page/home.html',
            controller: 'homeCtrl'
        })
        .when('/product', {
            templateUrl: 'page/product.html',
            controller: 'productCtrl'
        })
        .when('/about', {
            templateUrl: 'page/about.html',
            controller: 'aboutCtrl'
        })
});


app.controller('productCtrl', function ($scope, $http) {
    console.log('productCtrl');


    $scope.getListProduct = () => {
        $http({
            method: 'GET',
            url: 'http://localhost:8080/api/v1/products/list'
        }).then(function successCallback(response) {
            console.log('success', response);

            $scope.products =  response.data;
            $scope.size =  response.data.length;
            $scope.product = response.data[0];
        }, function errorCallback(response) {
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
            // basic authentication для REST API
            //{ "username": "admin", "password": "1"}
        }).then(function successCallback(response) {
            console.log('success', response);
            $scope.getListProduct();
           // $scope.products.push($scope.product);
            return response.data;
        }, function errorCallback(response) {
            console.log('error', response);
        });
    }

    $scope.deleteProduct = (id) => {
        $http({
            method: 'DELETE',
            url: 'http://localhost:8080/api/v1/products/' + id,
        }).then(function successCallback(response) {
            console.log('success', response);
            $scope.getListProduct();
        }, function errorCallback(response) {
            console.log('error', response);
        });
    }

    $scope.update = (product) => {
        $scope.product = product
    }

    $scope.getListProduct();
});


app.controller('homeCtrl', function ($scope) {
    console.log('HomeCtrl');
    $scope.model = {
        message: 'Home controller'
    }
});

app.controller('aboutCtrl', function ($scope) {
    console.log('aboutCtrl');
    $scope.model = {
        message: 'aboutCtrl'
    }
});