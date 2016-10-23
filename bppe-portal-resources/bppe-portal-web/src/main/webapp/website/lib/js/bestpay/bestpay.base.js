/**
 * Created by Howell on 2016/9/21.
 * Email:th15817161961@gmail.com
 */
var _url = window.location.href,
    _subclass = _url.substring(0, _url.indexOf('.html')+5);
    _subclass = _subclass.substring(_subclass.lastIndexOf('/') + 1, _subclass.indexOf('.html'));

require.config({
    baseUrl : "js",
    waitSeconds: 0,
    paths : {
        'bestpay.app' : '&CDN_Url&/lib/js/bestpay/bestpay.app',
        'bestpay.inject': '&CDN_Url&/lib/js/bestpay/bestpay.inject',
        'bestpay.lang' : '&CDN_Url&/lib/js/bestpay/bestpay.lang',
        'bestpay.ui' : '&CDN_Url&/lib/js/bestpay/bestpay.ui',
        'bestpay.http' : '&CDN_Url&/lib/js/bestpay/bestpay.http',
        'bestpay.global' : '/lib/js/bestpay/bestpay.global',
        'bestpay.header':'&CDN_Url&/lib/templates/header/js/bestpay.header',

        'Base64' : '&CDN_Url&/lib/js/SecurityPassword/Base64',
        'PassGuardCtrl' : '&CDN_Url&/lib/js/SecurityPassword/PassGuardCtrl',

        'jquery' : '&CDN_Url&/lib/js/thirdParty/jquery-1.12.4.min',
        'angular' : '&CDN_Url&/lib/js/thirdParty/angular.min',
        'angularUIRouter': '&CDN_Url&/lib/js/thirdParty/angular-ui-router.min',
        'angularCSS' : '&CDN_Url&/lib/js/thirdParty/angular-css.min',
        'header.router' : '&CDN_Url&/lib/templates/header/js/header.router',
        'bootstrap':'&CDN_Url&/lib/js/thirdParty/bootstrap.min',
        'bootstrapDatetimepicker':'&CDN_Url&/lib/js/thirdParty/bootstrap-datetimepicker.min',
        'bootstrapDatetimepickerZhCN':'&CDN_Url&/lib/js/thirdParty/bootstrap-datetimepicker.zh-CN',
        'highcharts':'&CDN_Url&/lib/js/thirdParty/highcharts.min',
        // 子应用特有
        'subconfig' : 'config',
        'subclass'  : _subclass
    },
    shim: {
        angular: ['jquery'],
        angularUIRouter: ['angular'],
        angularCSS: ['angular'],
        highcharts:['jquery'],
        bootstrap:['angular','jquery'],
        PassGuardCtrl:['jquery'],
        bootstrapDatetimepicker:['bootstrap','jquery'],
        bootstrapDatetimepickerZhCN:['bootstrap','jquery','bootstrapDatetimepicker']
    },
    urlArgs : function (id, url) {
        var args = "v=&version&";
        if( id == 'jquery' ||
            id == 'angular' ||
            id == 'highcharts' ||
            id == 'angularUIRouter' ||
            id == 'bootstrap' ||
            id == 'angularCSS' ||
            id == 'bootstrapDatetimepicker' ||
            id == 'bootstrapDatetimepickerZhCN' ||
            id == 'Base64' ||
            id == 'PassGuardCtrl'
        ){
            args = "v=1.0.0";
        }
        return (url.indexOf('?') === -1 ? '?' : '&') + args;
    }
});

if ('&debug&' != 'true') {
    console.log = function () {};
    console.info = function () {};
    console.error = function() {};
}

console.log("init : "+_subclass);

require(["bestpay.inject"], function() {});


