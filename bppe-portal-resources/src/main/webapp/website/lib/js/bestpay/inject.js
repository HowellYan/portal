/**
 * Created by yfzx_gd_yanghh on 2016/9/30.
 */
!(function () {
    'use strict';
    //当然了这个scripts的数据完全可以从服务器上动态获取回来加载
    var scripts = ['bestpay.global','subconfig',"inject"];

    require(scripts, function(config, subconfig) {
        // 将模块中的配置覆盖全局配置
        for (var prop in subconfig) {
            config[prop] = subconfig[prop];
        }
        window.config = config;
        require(['subclass'], function(subclass) {
            subclass;
            //渲染
            angular.bootstrap(document, ['app']);
        });
    });

}());