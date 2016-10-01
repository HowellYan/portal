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
        this.IndexMenuConfig();
        this.controller(subclass);
        this.PayMenuConfig();
    };

    //配置主页菜单路由
    BestpayRoute.prototype.IndexMenuConfig = function () {
        BestpayApp.config(['$stateProvider', '$urlRouterProvider',function($stateProvider, $urlRouterProvider) {
            console.log("init index router config");
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

    //配置pay菜单路由
    BestpayRoute.prototype.PayMenuConfig = function () {
        BestpayApp.config(['$stateProvider', '$urlRouterProvider',function($stateProvider, $urlRouterProvider) {
            console.log("init pay router config");
            $stateProvider.state('Pay.phonerechargecard', {
                url:"/phonerechargecard",
                templateUrl: "&CDN_Url&/pay/phonerechargecard/phonerechargecard.hbs?v=&version&",
                controller:"PayMenu"
            }).state('Pay.SDMpay', {
                url:"/SDMpay",
                templateUrl: "&CDN_Url&/pay/SDMpay/SDMpay.hbs?v=&version&",
                controller:"PayMenu"
            });
        }]);
    };


    BestpayRoute.prototype.controller = function (subclass) {
        BestpayApp.controller('headerMenu', ['$scope', '$location', function($scope,$location) {
            console.log($location.path());
            subclass.SetMenuHeader($location.path(),$scope);
        }]).controller('PayMenu', ['$scope', '$location', function($scope,$location) {
            console.log($location.path());
            //subclass.SetMenuHeader($location.path(),$scope);
        }]);
    };

}());