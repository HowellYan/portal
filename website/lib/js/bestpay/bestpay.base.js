/**
 * Created by Howell on 2016/9/21.
 * Email:th15817161961@gmail.com
 */
var _url = window.location.href,
    _subclass = _url.substring(0, _url.indexOf('.html')+5);
    _subclass = _subclass.substring(_subclass.lastIndexOf('/') + 1, _subclass.indexOf('.html'));
console.log("init : "+_subclass);
require.config({
    baseUrl : "js",
    waitSeconds: 0,
    paths : {
        'jquery' : '../../../lib/js/thirdParty/jquery-1.12.4.min',
        'angular' : '../../../lib/js/thirdParty/angular.min',
        'angular.route' : '../../../lib/js/thirdParty/angular-route.min',
        'bestpay.lang' : '../../../lib/js/bestpay/bestpay.lang',
        'bestpay.ui' : '../../../lib/js/bestpay/bestpay.ui',
        'bestpay.http' : '../../../lib/js/bestpay/bestpay.http',
        'global' : '../../../lib/js/bestpay/global',
        // 子应用特有
        'subconfig' : 'config',
        'subclass'  : _subclass
    },
    urlArgs : "v=&version&"
});


require(['global','subconfig'], function(config, subconfig) {
    // 将模块中的配置覆盖全局配置
    for (var prop in subconfig) {
        config[prop] = subconfig[prop];
    }

    require(['subclass','jquery','angular','angular.route'], function(subclass, $, ng,ng_route) {

        subclass;
    });
});
