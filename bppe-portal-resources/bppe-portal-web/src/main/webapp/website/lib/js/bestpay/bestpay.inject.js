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
            //渲染路由配置
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
        this.InquiryMenuConfig();
    };

    //配置主页菜单路由
    BestpayRoute.prototype.IndexMenuConfig = function () {
        //css file
        BestpayApp.config(['$cssProvider',function ($cssProvider) {
            angular.extend($cssProvider.defaults, {
                container: 'head',
                method: 'append',
                persist: true,
                preload: true,
                bustCache: false
            });
        }]);

        BestpayApp.config(['$stateProvider', '$urlRouterProvider',function($stateProvider, $urlRouterProvider) {
            console.log("init index router config");
            $stateProvider.state('Index', {
                url:"/Index",
                templateUrl: "&CDN_Url&/Index/index/index.hbs?v=&version&",
                controller:"headerMenu"
            }).state('Pay', {
                url:"/Pay",
                templateUrl: "&CDN_Url&/Pay/index/index.hbs?v=&version&",
                controller:"headerMenu"
            }).state('Finances', {
                url:"/Finances",
                templateUrl: "&CDN_Url&/Finances/index/index.hbs?v=&version&",
                controller:"headerMenu"
            }).state('Inquiry', {
                url:"/Inquiry",
                templateUrl: "&CDN_Url&/Inquiry/index/index.hbs?v=&version&",
                controller:"headerMenu"
            }).state('Account', {
                url:"/Account",
                templateUrl: "&CDN_Url&/Account/index/index.hbs?v=&version&",
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
                controller:"PayMenu",
                templateUrl: "&CDN_Url&/Pay/phonerechargecard/phonerechargecard.hbs?v=&version&"

            }).state('Pay.SDMpay', {
                url:"/SDMpay",
                controller:"PayMenu",
                templateUrl: "&CDN_Url&/Pay/SDMpay/SDMpay.hbs?v=&version&"

            });
        }]);
    };

    //配置inquiry菜单路由
    BestpayRoute.prototype.InquiryMenuConfig = function () {
        BestpayApp.config(['$stateProvider', '$urlRouterProvider',function($stateProvider, $urlRouterProvider) {
            console.log("init pay router config");
            $stateProvider.state('Inquiry.orderInquiry', {
                url:"/orderInquiry",
                controller:"InquiryMenu",
                templateUrl: "&CDN_Url&/Inquiry/orderInquiry/orderInquiry.hbs?v=&version&"

            })


        }]);
    };



    BestpayRoute.prototype.controller = function (subclass) {
        BestpayApp.controller('headerMenu', ['$scope', '$location', '$css', function($scope,$location,$css) {
            console.log($location.path());
            var path = $location.path().replace("/","");
            if(path.indexOf("/") > 0){
                path = path.substring(0, path.indexOf("/"));
            }
            $css.bind("&CDN_Url&/"+path+"/index/css/index.css?v=&version&",$scope);
            subclass.SetMenuHeader(path,$scope);

        }]).controller('PayMenu', ['$scope', '$location', '$css', function($scope,$location,$css) {
            var path = $location.path();
            console.log(path);
            $css.bind("&CDN_Url&"+path+"/css/"+path.replace("/Pay/","")+".css?v=&version&",$scope);
            require(["&CDN_Url&"+path+"/js/"+path.replace("/Pay/","")+".js?v=&version&"],function(PayItemClass){
                PayItemClass.initApp();
            });
        }]).controller('InquiryMenu',['$scope', '$location', '$css', function($scope,$location,$css) {
            var path = $location.path();
            console.log(path);
            $css.bind("&CDN_Url&"+path+"/css/"+path.replace("/Inquiry/","")+".css?v=&version&",$scope);
            require(["&CDN_Url&"+path+"/js/"+path.replace("/Inquiry/","")+".js?v=&version&"],function(InquiryItemClass){
                InquiryItemClass.initApp();
            });
        }]);
    };

}());