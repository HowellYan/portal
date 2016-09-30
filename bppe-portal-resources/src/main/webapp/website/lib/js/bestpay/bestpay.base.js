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
        'jquery' : '&CDN_Url&/lib/js/thirdParty/jquery-1.12.4.min',
        'angular' : '&CDN_Url&/lib/js/thirdParty/angular.min',
        'angularRoute' : '&CDN_Url&/lib/js/thirdParty/angular-route.min',
        'bestpay.lang' : '&CDN_Url&/lib/js/bestpay/bestpay.lang',
        'bestpay.ui' : '&CDN_Url&/lib/js/bestpay/bestpay.ui',
        'bestpay.http' : '&CDN_Url&/lib/js/bestpay/bestpay.http',
        'bestpay.global' : '&CDN_Url&/lib/js/bestpay/bestpay.global',
        'app' : '&CDN_Url&/lib/js/bestpay/app',
        'inject': '&CDN_Url&/lib/js/bestpay/inject',
        // 子应用特有
        'subconfig' : 'config',
        'subclass'  : _subclass
    },
    shim: {
        angular: ['jquery'],
        angularRoute: ["angular"]
    },
urlArgs : "v=&version&"
});

require(["inject"], function() {});


