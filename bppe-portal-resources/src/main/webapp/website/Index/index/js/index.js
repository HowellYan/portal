/**
 * Created by Howell on 2/10/16.
 */
define([],function(app) {
    function IndexAction() {

    }

    IndexAction.prototype.initApp = function(){
        console.log("init IndexAction");
    };


    return new IndexAction();
});