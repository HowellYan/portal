/**
 * Created by Howell on 2016/9/21.
 * Email:th15817161961@gmail.com
 */
define([],function() {
    function PayAction() {
    }

    PayAction.prototype.initApp = function(){
        $("#pay-menu").show();
        console.log("Init PayAction");
    };


    return new PayAction();
});