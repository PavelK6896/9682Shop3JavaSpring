
app.directive('filBar', () => {
    console.log('directive filBar');

    return {
        restrict: 'E',
        transclude: true,
        templateUrl: 'component/filter/filter.html',
        link: (scope, element, attrs, ctrl, transclude) => {
            transclude(scope, (clone, scope) => {
                element.append(clone); // добовляем в конец клон
            });

        }
    }
});

app.controller('f1',  ($scope, $http, $window) => {

    console.log($scope.m1)


})

