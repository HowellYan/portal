/**
 * Created by yfzx_gd_yanghh on 2016/9/25.
 */
define("app",["angular","angularRoute","jquery"], function() {
    var app = angular.module('app', ['ngRoute']);
        app.config(["$routeProvider",
            function($routeProvider) {
                console.log("b");
                var menuArray = config['HeaderMenuArray'];

                $routeProvider
                    .when('/Index',{template:'这是首页页面'})
                    .when('/Pay',{template:'/view/computers.html'})
                    .when('/Finances',{template:'这是打印机页面'})
                    .when('/Inquiry',{})
                    .when('/Account',{})
                    .otherwise({redirectTo:'/Index'});
            }]);
    return app;
});