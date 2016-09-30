/**
 * Created by Howell on 2016/9/21.
 * Email:th15817161961@gmail.com
 */
define(["app"],function(app) {
    function IndexAction() {
        this.initApp();
    }

    IndexAction.prototype.initApp = function () {
        console.log("IndexAction init!");
        require(["/lib/templates/header/js/bestpay.header.js"],function (header) {
            header;
        });
    };

    return new IndexAction();
});
