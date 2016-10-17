/**
 * Created by Howell on 2016/9/21.
 * Email:th15817161961@gmail.com
 */
define('subclass',["bestpay.app"],function(app) {
    function MainAction() {
        this.initApp();
        window.history.forward()
        //var blob=new Blob([{'msg':'Hello world'}],{type:'text/plain'});

        BestpayApp.controller('post',['$scope','$http',function($scope,$http){
            $http({
                method:'POST',
                url:'/api/index/main',
                data:{"msg":"Hi!"}
            }).success(function(data){
                $scope.main = data;
            });
        }]);
    }

    MainAction.prototype.initApp = function () {
        console.log("MainAction init!");
        $(".common-body-style").show();
    };

    MainAction.prototype.SetMenuHeader = function (path,$scope) {
        require(["bestpay.header"],function (header) {
            header.SelectItem(path);
        });
        $scope.message = "1111";
    };


    return new MainAction();
});
