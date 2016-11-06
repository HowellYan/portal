/**
 * Created by Howell on 4/10/16.
 */
define([],function() {
    function InquiryAction() {

    }

    InquiryAction.prototype.initApp = function(){
        $("#inquiry-menu").show();
        $("#inquiry-content").show();
        //this.setMenu();
        $('.tree-toggle').click(function () {
            $(this).parent().children('ul.tree').toggle(200);
        });
        console.log("Init InquiryAction");
    };


    return new InquiryAction();
});