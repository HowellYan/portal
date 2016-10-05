/**
 * Created by Howell on 2016/9/21.
 * Email:th15817161961@gmail.com
 */
define([],function() {
    function PayAction() {
    }

    PayAction.prototype.initApp = function() {
        $("#pay-menu").show();
        $("#pay-content").show();
        console.log("Init PayAction:"+config['pageName']);
        this.setMenu();
    };

    PayAction.prototype.setMenu = function() {
        var payMenuArray = config['PayConfig']['payMenu'],menuHTML="";
        for(var i = 0; i < payMenuArray.length; i++){
            var menuItem = payMenuArray[i];
            menuHTML += '<div ui-sref="'+menuItem['menuSref']+' href="'+menuItem['menuUrl']+'"">';
            menuHTML += '<a class="menu-item" href="'+menuItem['menuUrl']+'">'+menuItem['menuName']+'</a>';
            menuHTML += '</div>';
        }
        $("#pay-menu").html(menuHTML);

    };


    return new PayAction();
});