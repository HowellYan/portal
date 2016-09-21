/**
 * Created by Howell on 2016/9/21.
 * Email:th15817161961@gmail.com
 */
require.config({
    baseUrl : "js",
    waitSeconds: 0,
    paths : {
        'jquery' : '../../../lib/js/thirdParty/jquery-1.12.4.min',
        'angular' : '../../../lib/js/thirdParty/angular.min',
        'bestpay.lang' : '../../../lib/js/bestpay/bestpay.lang',
        'bestpay.ui' : '../../../lib/js/bestpay/bestpay.ui',
        'bestpay.http' : '../../../lib/js/bestpay/bestpay.http',
        'global' : '../../../lib/js/bestpay/global'
    },
    shim: {
        'jtemplates' : ['jquery']
    },
    urlArgs : "v=&version&"
});


require(['jquery', 'angular'], function($, ng) {



});
