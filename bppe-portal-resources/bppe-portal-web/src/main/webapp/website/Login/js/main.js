/**
 * Created by Howell on 2016/9/21.
 * Email:th15817161961@gmail.com
 */
define('subclass',["bestpay.app"],function(app) {
    function LoginMainAction() {
        this.initApp();
        window.history.forward()
    }

    LoginMainAction.prototype.initApp = function () {
        console.log("MainAction init!");
        $(".common-body-style").show();
    };
    LoginMainAction.prototype.SetMenuHeader = function (path,$scope) {

        //$scope.message = "1111";
    };

    return new LoginMainAction();
});