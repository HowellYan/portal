/**
 * Created by yfzx_gd_yanghh on 2016/9/25.
 */
define(['bestpay.lang'],function(Lang) {
    function Http() {

    }
    Http.prototype.initApp = function () {

    };


    Http.prototype.setCommonParams = function (params) {
        params.WebKeep = Lang.getKeep();

        return params;
    };

    /**
     * Ajax call to Web Service
     * @param settings 字段：service, params, success, error, showLoading, setTime, async, openInject
     */
    Http.prototype.callWebService = function(settings){
        config.isOpen = true;
        if (settings.showLoading !== false) {

        }

        if(settings.setTime == null){
            settings.setTime = 75000;
        }

        if(settings.openInject == null){
            settings.openInject = false;
        }

        if (typeof settings.error !== 'function') {
            settings.error = function () {
                console.log("error 请求失败: " + url);
            }
        }
        var successCallBack = function(result) {
            console.log('successCallBack response: ' + JSON.stringify(result));
            config.isOpen = true;
            if(settings.openInject){
                var global_url = '/lib/js/bestpay/bestpay.global.js?v=' + Date.parse(new Date());
                require([global_url],function(config){
                    for (var prop in config) {
                        window.config[prop] = config[prop];
                    }
                    settings.success(result);
                });
            } else {
                settings.success(result);
            }
        };

        var errorCallBack = function(result){
            console.log('successCallBack response: ' + JSON.stringify(result));
            config.isOpen = true;

            settings.error(result);
        };

        if (typeof settings.params === 'object') {
            settings.params = JSON.stringify(settings.params);
        }

        var suffix = '?ran' + Math.floor(Math.random() * 100 + 1) + '=' + Date.parse(new Date());

        if(settings.URL != null){
            url = settings.URL + settings.service + suffix;
        } else {
            url = settings.service + suffix;
        }
        if(settings.async == null){
            settings.async = true;
        }
        console.log('isOpen: ' + config.isOpen);
        if(config.isOpen == false){
            return;
        }else{
            config.isOpen = false;
        }
        console.log('url: ' + url);
        console.log('request: ' + settings.params);
        try {
            window.jqXHR = $.ajax({
                url: url,
                type: "POST",
                async: settings.async,
                data: settings.params,
                timeout: settings.setTime,
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                success: successCallBack,
                error: errorCallBack
            });
        } catch (e) {
            console.error('try catch error: ' + e.message);
            config.isOpen = true;
            settings.error(result);
        }
    };


    return new Http();
});