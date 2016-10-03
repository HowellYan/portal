/**
 * Created by yfzx_gd_yanghh on 2016/9/25.
 */
define("bestpay.app",["angular","angularUIRouter","jquery","angularCSS"], function() {

    var app = angular.module('bestpay.app', ['ui.router','angularCSS']);
    app.config(["$sceDelegateProvider",function ($sceDelegateProvider) {
        $sceDelegateProvider.resourceUrlWhitelist([
            // Allow same origin resource loads.
            'self',
            // Allow loading from outer templates domain.
            'http://192.168.0.104:8080/lib/templates/footer/**'
        ]);
    }]);
    window.BestpayApp = app;



    return app;
});

