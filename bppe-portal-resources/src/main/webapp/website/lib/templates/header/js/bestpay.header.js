/**
 * Created by Howell on 2016/9/21.
 * Email:th15817161961@gmail.com
 */

define(['/lib/templates/header/js/config.js'],function (headerConfig) {
    var Header = function () {
        this.initApp();
    };

    Header.prototype.initApp = function () {
        var menuArray = headerConfig['menuArray'], menuHTML = "";
        for(var i = 0; i < menuArray.length; i++){
            var menuItem = menuArray[i];
            menuHTML+='<li tip="'+menuItem['menuTip']+'" class="menu" ' +
                'url="'+menuItem['menuUrl']+'" select="true" menuId="'+menuItem['menuID']+'">';
            menuHTML += '<a href="'+menuItem['menuUrl']+'" class="menu-link menu-select">'+menuItem['menuName']+'</a> </li>'
        }
        $("#index_menu").html(menuHTML);
        console.log("a");

        var bestpayApp = angular.module('bestpayApp',['ngRoute']);
        bestpayApp.config(['$routeProvider', function($routeProvider){
                    console.log("b");
                    $routeProvider
                        .when('/index',{template:'这是首页页面'})
                        .when('/pay',{template:'/view/computers.html'})
                        .when('/account',{template:'这是打印机页面'})
                        .otherwise({redirectTo:'/index'});

                }]);
        console.log(bestpayApp);
    };

    return new Header();
});


