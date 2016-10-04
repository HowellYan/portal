/**
 * Created by Howell on 2016/9/21.
 * Email:th15817161961@gmail.com
 */

define('bestpay.header',[],function () {
    var Header = function () {
        this.initApp();
    };

    Header.prototype.initApp = function () {
        var menuArray = config['HeaderMenuArray'], menuHTML = "";
        for(var i = 0; i < menuArray.length; i++){
            var menuItem = menuArray[i];
            menuHTML += '<li tip="'+menuItem['menuTip']+'" class="menu" ' + 'url="';
            menuHTML += menuItem['menuUrl']+'" select="true" menuId="'+menuItem['menuID']+'">';
            menuHTML += '<a href="'+menuItem['menuUrl']+'"';
            if(i == 0){
                menuHTML += ' class="menu-link menu-select">';
            } else {
                menuHTML += ' class="menu-link">';
            }
            menuHTML += menuItem['menuName']+'</a> </li>'
        }
        $("#index_menu").html(menuHTML);
    };

    Header.prototype.SelectItem = function (path) {
        $('#index_menu .menu-select').removeClass("menu-select");
        $('#index_menu .menu-link').each(function () {
            var thisPath = $(this).attr("href").replace("#","");
            if(thisPath == path){
                $(this).addClass("menu-select");
            }
        });
        this.InjectJS(path);
    };

    Header.prototype.InjectJS = function(path){
        require(["&CDN_Url&/"+path+"/index/js/config.js"],function(config){
            // 将模块中的配置覆盖全局配置
            for (var prop in config) {
                window.config[prop] = config[prop];
            }

            require(["&CDN_Url&/"+path+"/index/js/index.js"],function(menuClass){
                menuClass.initApp();
                console.log("InjectJS:"+path);
            });

        });
    };

    return new Header();
});


