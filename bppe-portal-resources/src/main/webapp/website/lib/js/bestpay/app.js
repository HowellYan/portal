/**
 * Created by yfzx_gd_yanghh on 2016/9/25.
 */
define("app",["angular","angularRoute","jquery"], function() {
    var app = angular.module('app', ['ngRoute']);
        app.config(["$routeProvider", function($routeProvider) {
                console.log("b");
                $routeProvider
                    .when('/Index',{templateUrl:'&CDN_Url&/index/index.hbs?v=&version&',controller:'headerMenu'})
                    .when('/Pay',{templateUrl:'&CDN_Url&/pay/index/index.hbs?v=&version&',controller:'headerMenu'})
                    .when('/Finances',{templateUrl:'/',controller:'headerMenu'})
                    .when('/Inquiry',{templateUrl:'&CDN_Url&/inquiry/index/index.hbs?v=&version&',controller:'headerMenu'})
                    .when('/Account',{templateUrl:'&CDN_Url&/account/index/index.hbs?v=&version&',controller:'headerMenu'})
                    .otherwise({redirectTo:'/Index'});
        }]);
        app.controller('headerMenu', ["$scope", "$routeParams", '$location', function($scope, $routeParams,$location) {
            $scope.params = $routeParams;
            $scope.message = "1111";
            console.log($location);

            console.log($location.path());
            $("#index_menu .menu").(function (item) {
                console.log(item);
            })
        }]);

    return app;
});