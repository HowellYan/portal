/**
 * Created by Howell on 3/10/16.
 */
define([],function() {
    function AccountAction() {

    }

    AccountAction.prototype.initApp = function(){
        console.log("Init AccountAction:"+config['pageName']);
    };


    return new AccountAction();
});