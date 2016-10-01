/**
 * Created by yfzx_gd_yanghh on 2016/9/30.
 */
!(function () {
    'use strict';
    //当然了这个scripts的数据完全可以从服务器上动态获取回来加载
    var scripts = ['bestpay.global','subconfig',"bestpay.inject"];

    require(scripts, function(config, subconfig) {
        // 将模块中的配置覆盖全局配置
        for (var prop in subconfig) {
            config[prop] = subconfig[prop];
        }
        window.config = config;
        require(['subclass'], function(subclass) {

            BestpayApp.config(["$routeProvider", function($routeProvider) {
                console.log("b");
                $routeProvider
                    .when('/Index',{templateUrl:'&CDN_Url&/index/index.hbs?v=&version&',controller:'headerMenu'})
                    .when('/Pay',{templateUrl:'&CDN_Url&/pay/index/index.hbs?v=&version&',controller:'headerMenu'})
                    .when('/Finances',{templateUrl:'/?v=&version&',controller:'headerMenu'})
                    .when('/Inquiry',{templateUrl:'&CDN_Url&/inquiry/index/index.hbs?v=&version&',controller:'headerMenu'})
                    .when('/Account',{templateUrl:'&CDN_Url&/account/index/index.hbs?v=&version&',controller:'headerMenu'})
                    .otherwise({redirectTo:'/Index'});
            }]);
            BestpayApp.controller('headerMenu', ["$scope", "$routeParams", '$location', function($scope, $routeParams,$location) {
                $scope.params = $routeParams;
                console.log($location.path());
                subclass.SetMenuHeader($location.path(),$scope);
            }]);

            //渲染
            angular.bootstrap(document, ['bestpay.app']);
        });
    });



}());