/**
 * Created by Howell on 2016/9/21.
 * Email:th15817161961@gmail.com
 */
define('subclass',["bestpay.app","bestpay.ui"],function(app,UI) {
    function LoginMainAction() {
        this.initApp();
        window.history.forward()
    }

    LoginMainAction.prototype.initApp = function () {
        console.log("MainAction init!");
        $(".common-body-style").show();

        $("#GetPwd").click(function () {
            alert(UI.getPasswordResult("loginpwd"));
            $("#machineNetwork").val(BASE64.encoder(PassGuardCtrlloginpwd.machineNetwork()));
            $("#loginpwd").val(BASE64.encoder(PassGuardCtrlloginpwd.pwdResult()));
            $("#machineCPU").val(BASE64.encoder(PassGuardCtrlloginpwd.machineCPU()));
            $("#machineDisk").val(BASE64.encoder(PassGuardCtrlloginpwd.machineDisk()));
            $("#id_submit").click();
        });

    };

    LoginMainAction.prototype.SetMenuHeader = function (path,$scope) {
        //$scope.message = "1111";
    };

    return new LoginMainAction();
});
