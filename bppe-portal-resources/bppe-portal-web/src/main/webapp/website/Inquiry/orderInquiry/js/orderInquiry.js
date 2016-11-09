/**
 * Created by Howell on 5/11/16.
 */
define(["bestpay.http", "bestpay.lang",'jqueryDataTables','bootstrapDatetimepicker','bootstrapDatetimepickerZhCN'],function(HTTP, Lang) {
    function OrderInquiry() {

    }
    OrderInquiry.prototype.initApp = function () {
        this.btnInit();

    };

    OrderInquiry.prototype.btnInit = function () {
        var self = this;
        //StartTime
        $("#id_StartTime").val(Lang.getYYYYMMDD());
        $('#id_StartTime').datetimepicker({
            language:  'zh-CN',
            format: 'yyyy-M-dd',
            weekStart: 1,
            todayBtn:  1,
            autoclose: 1,
            todayHighlight: 1,
            startView: 2,
            minView: 2,
            forceParse: 0
        });
        $("#id_CleanStart").click(function () {
            $("#id_StartTime").val("");
        });

        //EndTime
        $("#id_EndTime").val(Lang.getYYYYMMDD());
        $('#id_EndTime').datetimepicker({
            language:  'zh-CN',
            format: 'yyyy-M-dd',
            weekStart: 1,
            todayBtn:  1,
            autoclose: 1,
            todayHighlight: 1,
            startView: 2,
            minView: 2,
            forceParse: 0
        });
        $("#id_CleanEnd").click(function () {
            $("#id_EndTime").val("");
        });

        //
        $("#id_inquiry_btn").click(function () {
            self.callSetOrderInquiryData();
        });
    };

    OrderInquiry.prototype.callSetOrderInquiryData = function(){
        var self = this;
        HTTP.callWebService({
            'service': '/api/inquiry/orderInquiry',
            'params': self.callSetOrderInquiryDataParams(),
            'showLoading': false,
            'openInject' : false,
            'success': self.callSetOrderInquiryDataSuccessCallback
        });
    };
    OrderInquiry.prototype.callSetOrderInquiryDataParams = function () {
        var params = {};
        params = HTTP.setCommonParams(params);
        params['StartTime'] = $("#id_StartTime").val();
        return params;
    };

    OrderInquiry.prototype.callSetOrderInquiryDataSuccessCallback = function (result) {
        $('#example').DataTable({
            "ajax": "/Inquiry/orderInquiry/orderInquiryData.hbs",
            "columns": [
                { "data": "name" },
                { "data": "position" },
                { "data": "office" },
                { "data": "extn" },
                { "data": "start_date" },
                { "data": "salary" }
            ]
        });
    };

    return new OrderInquiry();
});