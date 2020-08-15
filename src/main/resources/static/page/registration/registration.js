
app.directive('ngMyvalidationcheck', () => {
    console.log('ngMyvalidationcheck');
    return {
        require: 'ngModel',

        link: (scope, element, attrs, ctrl, transclude) => {
            function myValidation(value) {
                if (value.match(/^a/)){ // регулярное в ^a начало
                    ctrl.$setValidity("firstA",true);
                } else{
                    ctrl.$setValidity("firstA", false);
                }
                return true;
            }
            ctrl.$parsers.push(myValidation);
        }
    }
});

app.controller('registrationCtrl', function ($scope) {
    console.log('registrationCtrl');
});
