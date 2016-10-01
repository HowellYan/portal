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
            if($(this).attr("href").replace("#","")==path.replace("/","")){
                $(this).addClass("menu-select");
            }
        });
    };

    return new Header();
});


