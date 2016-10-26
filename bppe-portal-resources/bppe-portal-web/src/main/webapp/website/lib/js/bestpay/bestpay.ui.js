/**
 * Created by yfzx_gd_yanghh on 2016/9/25.
 */
define(["Base64","PassGuardCtrl"],function() {
    function UI() {

    }
    UI.prototype.initApp = function () {

    };
    /**
     * 获取安全控件密码
     * @param id
     * @returns {*}
     */
    UI.prototype.getPasswordResult = function (id) {
        var obj = window['SECURITY_PASSWORD_'+id];
        if(obj != null){
            return BASE64.encoder(obj.pwdResult());
        } else {
            return '';
        }
    };

    /**
     * 获取安全控件mac地址
     * @param id
     * @returns {*}
     */
    UI.prototype.getPasswordMachineNetwork = function (id) {
        var obj = window['SECURITY_PASSWORD_'+id];
        if(obj != null){
            return BASE64.encoder(obj.machineNetwork());
        } else {
            return '';
        }
    };

    /**
     * 获取安全控件CPU ID
     * @param id
     * @returns {*}
     */
    UI.prototype.getPasswordMachineCPU = function (id) {
        var obj = window['SECURITY_PASSWORD_'+id];
        if(obj != null){
            return BASE64.encoder(obj.machineCPU());
        } else {
            return '';
        }
    };

    /**
     * 获取安全控件磁盘ID
     * @param id
     * @returns {*}
     */
    UI.prototype.getPasswordMachineDisk = function (id) {
        var obj = window['SECURITY_PASSWORD_'+id];
        if(obj != null){
            return BASE64.encoder(obj.machineDisk());
        } else {
            return '';
        }
    };

    /**
     * 获取mac地址
     * @returns {*}
     */
    UI.prototype.getMachineNetwork = function () {
        var obj = new $.pge({pgeClass:"hiddenPWD",pgeId: "SecurityScript-self"});
        var divObj = document.createElement("div");
            divObj.id = "_id_SecurityScript_Show";
        document.body.appendChild(divObj);
        obj.generate("_id_SecurityScript_Show");
        obj.pgInitialize();
        var injections = config['injections'];
        obj.pwdSetSk(injections['SecurityScriptRD']);
        var machineNetwork = BASE64.encoder(obj.machineNetwork());
        document.body.removeChild(divObj);
        return machineNetwork;
    };

    return new UI();
});