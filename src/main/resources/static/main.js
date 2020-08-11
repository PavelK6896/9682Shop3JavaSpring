var app = angular.module('app', ['ngRoute']);

app.config(function ($routeProvider) {
    console.log("$routeProvider")
    $routeProvider
        .when('/', {
            templateUrl: 'page/home/home.html',
            controller: 'homeCtrl'
        })
        .when('/product', {
            templateUrl: 'page/product/product.html',
            controller: 'productCtrl'
        })
        .when('/about', {
            templateUrl: 'page/about/about.html',
            controller: 'aboutCtrl'
        })
});

