/**
 * Created by yfzx_gd_yanghh on 2016/9/25.
 */
define(["Base64"],function() {
    function UI() {

    }
    UI.prototype.initApp = function () {

    };

    UI.prototype.getPasswordResult = function (id) {
        if(window.SECURITY_PASSWORD != null && window.SECURITY_PASSWORD[id] != null){
            var obj = window.SECURITY_PASSWORD[id];
            return BASE64.encoder(obj.pwdResult());
        } else {
            return '';
        }
    };

    return new UI();
});