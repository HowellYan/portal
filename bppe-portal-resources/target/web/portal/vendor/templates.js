(function() {
  var template = Handlebars.template, templates = Handlebars.templates = Handlebars.templates || {};
  templates["official/MyLove/index-head/view"] = template({"1":function(container,depth0,helpers,partials,data) {
    return "    <div class=\"head\"></div>\r\n\r\n\r\n";
},"compiler":[7,">= 4.0.0"],"main":function(container,depth0,helpers,partials,data) {
    var stack1;

  return ((stack1 = (helpers.component || (depth0 && depth0.component) || helpers.helperMissing).call(depth0 != null ? depth0 : {},"index-head",{"name":"component","hash":{},"fn":container.program(1, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "");
},"useData":true});
templates["official/MyLove/index-li/view"] = template({"1":function(container,depth0,helpers,partials,data) {
    var stack1;

  return "    <div class=\"divheaderShadow\"></div>\r\n    <div id=\"full-screen-slider\">\r\n        <ul id=\"slides\">\r\n"
    + ((stack1 = helpers.each.call(depth0 != null ? depth0 : {},(depth0 != null ? depth0._DATA_ : depth0),{"name":"each","hash":{},"fn":container.program(2, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "        </ul>\r\n    </div>\r\n";
},"2":function(container,depth0,helpers,partials,data) {
    var helper;

  return "                <li>"
    + container.escapeExpression(((helper = (helper = helpers.appId || (depth0 != null ? depth0.appId : depth0)) != null ? helper : helpers.helperMissing),(typeof helper === "function" ? helper.call(depth0 != null ? depth0 : {},{"name":"appId","hash":{},"data":data}) : helper)))
    + "-"
    + container.escapeExpression(((helper = (helper = helpers.appName || (depth0 != null ? depth0.appName : depth0)) != null ? helper : helpers.helperMissing),(typeof helper === "function" ? helper.call(depth0 != null ? depth0 : {},{"name":"appName","hash":{},"data":data}) : helper)))
    + "</li>\r\n";
},"compiler":[7,">= 4.0.0"],"main":function(container,depth0,helpers,partials,data) {
    var stack1;

  return ((stack1 = (helpers.component || (depth0 && depth0.component) || helpers.helperMissing).call(depth0 != null ? depth0 : {},"index-li",{"name":"component","hash":{},"fn":container.program(1, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "");
},"useData":true});
templates["official/MyLove/index/view"] = template({"1":function(container,depth0,helpers,partials,data) {
    return "    <div id=\"show\" class=\"show\">\r\n       中文 Hello !\r\n    </div>\r\n";
},"compiler":[7,">= 4.0.0"],"main":function(container,depth0,helpers,partials,data) {
    var stack1;

  return ((stack1 = (helpers.component || (depth0 && depth0.component) || helpers.helperMissing).call(depth0 != null ? depth0 : {},"index",{"name":"component","hash":{},"fn":container.program(1, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "");
},"useData":true});
templates["welcome"] = template({"compiler":[7,">= 4.0.0"],"main":function(container,depth0,helpers,partials,data) {
    return "<p>Hello world! This is Linner Boilerplate.</p>\r\n";
},"useData":true});

})();
