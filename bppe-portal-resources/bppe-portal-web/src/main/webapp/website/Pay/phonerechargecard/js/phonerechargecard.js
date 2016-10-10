/**
 * Created by Howell on 1/10/16.
 */
define([],function() {
    function PhonerechargeCard() {
        
    }
    PhonerechargeCard.prototype.initApp = function () {
        $('#myTab a').click(function (e) {
            e.preventDefault();
            $(this).tab('show')
        })
    };

    return new PhonerechargeCard();
});