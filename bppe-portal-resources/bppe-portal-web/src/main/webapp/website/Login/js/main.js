/**
 * Created by Howell on 2016/9/21.
 * Email:th15817161961@gmail.com
 */
define('subclass',["bestpay.app","Base64","PassGuardCtrl"],function(app) {
    function LoginMainAction() {
        this.initApp();
        window.history.forward()
    }

    LoginMainAction.prototype.initApp = function () {
        console.log("MainAction init!");
        $(".common-body-style").show();

        // BestpayApp.controller('AppController', ['$scope','$sce',function($scope,$sce) {
        //     var url = $sce.trustAs($sce.HTML,"http://localhost:8080/webCommon/SecurityPassword/view/LoginPasswordView.hbs");
        //     console.log(url);
        //     $scope.trustSrc = url;
        // }]);

    };

    LoginMainAction.prototype.SetMenuHeader = function (path,$scope) {
        //$scope.message = "1111";
    };

    return new LoginMainAction();
});
