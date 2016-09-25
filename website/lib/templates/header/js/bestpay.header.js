/**
 * Created by Howell on 2016/9/21.
 * Email:th15817161961@gmail.com
 */

require(['/lib/templates/header/js/config.js'],function (config) {

    var menuArray = config['menuArray'], menuHTML = "";
    for(var i = 0; i < menuArray.length; i++){
        var menuItem = menuArray[i];
        menuHTML+='<li tip="'+menuItem['menuTip']+'" class="menu" ' +
            'url="'+menuItem['menuUrl']+'" select="true" menuId="'+menuItem['menuID']+'">';
        menuHTML += '<a href="'+menuItem['menuUrl']+'" class="menu-link menu-select">'+menuItem['menuName']+'</a> </li>'
    }

    $("#index_menu").html(menuHTML);

    var scotchApp = angular.module('scotchApp', ['ngRoute']);

// configure our routes
    scotchApp.config(function($routeProvider) {
        $routeProvider
        // route for the home page
            .when('/index', {
                templateUrl : '/index/index.hbs',
                controller  : 'mainController'
            })

            // route for the about page
            .when('/pay', {
                templateUrl : '/pay/index/index.hbs',
                controller  : 'aboutController'
            })

            // route for the contact page
            .when('/account', {
                templateUrl : '/account/index/index.hbs',
                controller  : 'contactController'
            });
    });

    // create the controller and inject Angular's $scope
    scotchApp.controller('mainController', function($scope) {
        // create a message to display in our view
        $scope.message = 'Everyone come and see how good I look!';
        console.log('mainController');
    });

    scotchApp.controller('aboutController', function($scope) {
        $scope.message = 'Look! I am an about page.';
        console.log('aboutController');
    });

    scotchApp.controller('contactController', function($scope) {
        $scope.message = 'Contact us! JK. This is just a demo.';
        console.log('contactController');
    });

});
