/**
 * Created by Howell on 5/11/16.
 */
define(["bestpay.http", "bestpay.lang",'bootstrapDatetimepicker','bootstrapDatetimepickerZhCN'],function(HTTP, Lang) {
    function OrderInquiry() {

    }
    OrderInquiry.prototype.initApp = function () {
        $('.form_date').datetimepicker({
            language:  'zh-CN',
            weekStart: 1,
            todayBtn:  1,
            autoclose: 1,
            todayHighlight: 1,
            startView: 2,
            minView: 2,
            forceParse: 0
        });
        $("#id_StartTime").val(Lang.getYYYYMMDD());
    };

    return new OrderInquiry();
});