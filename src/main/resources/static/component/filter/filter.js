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


let set1 = new Set();


app.controller('f1', ($scope, $http, $window, factory, $rootScope) => {

    $scope.maxP = 500
    $scope.minP = 10
    $scope.name = ''

    $http({
        method: 'GET',
        url: 'http://localhost:8080/api/v1/products/categories'
    }).then((response) => {
        console.log(response.data)
        $scope.categories = response.data
    }, (response) => {
        console.log('error', response);
    });


    $scope.change = (id) => {



        if (set1.has(id)) {
            set1.delete(id)
        } else {
            set1.add(id);
        }
    }


    $scope.getFilter = () => {
        console.log("getFilter")
        const dd ={
            name: $scope.name,
            max: $scope.maxP,
            min: $scope.minP,
            set: Array.from(set1)
        }
        $rootScope.$emit("getListProductFilter", { dd}); //import

    }
})


