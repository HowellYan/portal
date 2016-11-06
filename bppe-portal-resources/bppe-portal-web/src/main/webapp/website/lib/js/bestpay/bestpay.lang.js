/**
 * Created by yfzx_gd_yanghh on 2016/9/25.
 */
define([],function() {
    function Lang() {

    }

    Lang.prototype.initApp = function () {

    };

    Lang.prototype.getKeep = function () {
        var keep = '';
        var date = new Date();
        var y = date.getFullYear();
        var m = date.getMonth() + 1;
        m = m < 10 ? '0' + m : m;
        var d = date.getDate() < 10 ? '0' + date.getDate() : date.getDate();
        var h = date.getHours() < 10 ? '0' + date.getHours() : date.getHours();
        var f = date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes();
        var s = date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds();
        var rand = Math.round(Math.random()*8999+1000);
        keep = '020' + y + '' + m + '' + d + '' + h + '' + f + '' + s + '' + rand;
        return keep;
    };

    Lang.prototype.getYYYYMMDD = function () {
        var data = '';
        var date = new Date();
        var y = date.getFullYear();
        var m = date.getMonth() + 1;
        m = m < 10 ? '0' + m : m;
        var d = date.getDate() < 10 ? '0' + date.getDate() : date.getDate();
        data = y + '-' + m + '-' + d;
        return data;
    };

    return new Lang();
});