/**
 * Created by Howell on 2016/9/21.
 * Email:th15817161961@gmail.com
 */
define(['bestpay.http'],function(HTTP) {
    function IndexAction() {
        alert(1);
    }

    IndexAction.prototype.initApp = function () {

    };

    return new IndexAction();
});
