/**
 * Created by yfzx_gd_yanghh on 2016/9/25.
 */
define("bestpay.app",["angular","angularUIRouter","jquery","angularCSS","bootstrap","Base64","PassGuardCtrl"], function() {

    var app = angular.module('bestpay.app', ['ui.router','angularCSS']);
    app.config(["$sceDelegateProvider",function ($sceDelegateProvider) {
        // $sceDelegateProvider.resourceUrlWhitelist([
        //     // Allow same origin resource loads.
        //     'self'
        // ]);
    }]);
    window.BestpayApp = app;



    return app;
});

