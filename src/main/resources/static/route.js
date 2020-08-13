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
        .when('/add', {
            templateUrl: 'page/admin/add_product.html',
            controller: 'addProductCtrl'
        })
        .when('/profile', {
            templateUrl: 'page/profile/profile.html',
            controller: 'profileCtrl'
        })
});
