/**
 * Created by yfzx_gd_yanghh on 2016/9/30.
 */
!(function () {
    'use strict';
    var scripts = ['bestpay.global','subconfig',"bestpay.inject"];

    require(scripts, function(config, subconfig) {
        // 将模块中的配置覆盖全局配置
        for (var prop in subconfig) {
            config[prop] = subconfig[prop];
        }
        window.config = config;
        require(['subclass'], function(subclass) {
            new BestpayRoute(subclass);
            //渲染
            angular.bootstrap(document, ['bestpay.app']);
        });
    });

    //路由配置
    var BestpayRoute = function (subclass) {
        this.config();
        this.controller(subclass);
    };

    BestpayRoute.prototype.config = function () {
        BestpayApp.config(['$stateProvider', '$urlRouterProvider',function($stateProvider, $urlRouterProvider) {
            console.log("b");
            $stateProvider.state('Index', {
                url:"/Index",
                templateUrl: "&CDN_Url&/index/index.hbs?v=&version&",
                controller:"headerMenu"
            }).state('Pay', {
                url:"/Pay",
                templateUrl: "&CDN_Url&/pay/index/index.hbs?v=&version&",
                controller:"headerMenu"
            }).state('Finances', {
                url:"/Finances",
                templateUrl: "/?v=&version&",
                controller:"headerMenu"
            }).state('Inquiry', {
                url:"/Inquiry",
                templateUrl: "&CDN_Url&/inquiry/index/index.hbs?v=&version&",
                controller:"headerMenu"
            }).state('Account', {
                url:"/Account",
                templateUrl: "&CDN_Url&/account/index/index.hbs?v=&version&",
                controller:"headerMenu"
            });
            $urlRouterProvider.otherwise('/Index');
        }]);
    };

    BestpayRoute.prototype.controller = function (subclass) {
        BestpayApp.controller('headerMenu', ['$scope', '$location', function($scope,$location) {
            console.log($location.path());
            subclass.SetMenuHeader($location.path(),$scope);
        }]);
    };

}());