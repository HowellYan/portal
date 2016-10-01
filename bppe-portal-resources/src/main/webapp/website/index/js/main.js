/**
 * Created by Howell on 2016/9/21.
 * Email:th15817161961@gmail.com
 */
define('subclass',["bestpay.app"],function(app) {
    function IndexAction() {
        this.initApp();
    }

    IndexAction.prototype.initApp = function () {
        console.log("IndexAction init!");

    };

    IndexAction.prototype.SetMenuHeader = function (path,$scope) {
        require(["bestpay.header"],function (header) {
            header.SelectItem(path);
        });
        $scope.message = "1111";
    };

    return new IndexAction();
});
