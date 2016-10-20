/**
 * Created by Howell on 1/10/16.
 */
define(['highcharts','bestpay.http'],function(highcharts, HTTP) {
    function PhonerechargeCard() {
        //var blob=new Blob([{'msg':'Hello world'}],{type:'text/plain'});

        // BestpayApp.controller('post',['$scope','$http',function($scope,$http){
        //     $http({
        //         method:'POST',
        //         url:'/api/index/main',
        //         data:{"msg":"Hi!"}
        //     }).success(function(data){
        //         $scope.main = data;
        //     });
        // }]);
    }
    PhonerechargeCard.prototype.initApp = function () {
        $('#myTab a').click(function (e) {
            e.preventDefault();
            $(this).tab('show')
        })
        this.dataShow();
        this.callMain();
    };


    PhonerechargeCard.prototype.callMain = function () {
        var self = this;
        HTTP.callWebService({
            'service': '/api/index/main',
            'params': self.callMainParams(),
            'showLoading': false,
            'openInject' : true,
            'success': self.callMainSuccessCallback
        });
    };

    PhonerechargeCard.prototype.callMainParams = function () {
        var params = {};
        params = HTTP.setCommonParams(params);
        return params;
    };

    PhonerechargeCard.prototype.callMainSuccessCallback = function (result) {

        var injections = config['injections'];

        console.log(injections['UserInfoModel']['custName']);

        $("#id_main").html(JSON.stringify(result));

    };



    PhonerechargeCard.prototype.dataShow = function () {
        $('#container').highcharts({
            title: {
                text: 'Monthly Average Temperature',
                x: -20 //center
            },
            subtitle: {
                text: 'Source: WorldClimate.com',
                x: -20
            },
            xAxis: {
                categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun',
                    'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
            },
            yAxis: {
                title: {
                    text: 'Temperature (°C)'
                },
                plotLines: [{
                    value: 0,
                    width: 1,
                    color: '#808080'
                }]
            },
            tooltip: {
                valueSuffix: '°C'
            },
            legend: {
                layout: 'vertical',
                align: 'right',
                verticalAlign: 'middle',
                borderWidth: 0
            },
            series: [{
                name: 'Tokyo',
                data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]
            }, {
                name: 'New York',
                data: [-0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5]
            }, {
                name: 'Berlin',
                data: [-0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6, 17.9, 14.3, 9.0, 3.9, 1.0]
            }, {
                name: 'London',
                data: [3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8]
            }]
        });

    };

    return new PhonerechargeCard();
});