


app.controller('descriptionCtrl', function ($scope, $location, $templateCache, $routeParams ) {
    console.log('Ctrl11');

    $scope.description = 'description js'
    $scope.m1 = 5
    $scope.id = $routeParams["id"]

    console.log($templateCache)
    console.log($location)

    $scope.id2 = $location.$$absUrl



});
