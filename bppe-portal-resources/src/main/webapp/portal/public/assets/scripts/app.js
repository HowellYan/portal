this.require.define({"common/commonjs_helper":function(exports, require, module){(function() {
  var hasModule, requireByRegex;

  requireByRegex = function(regex) {
    var modules;
    modules = window.require.modules;
    return $.each(modules, function(k) {
      if (regex.test(k)) {
        return require(k);
      }
    });
  };

  hasModule = function(path) {
    return window.require.modules[path] !== void 0;
  };

  module.exports = {
    requireByRegex: requireByRegex,
    hasModule: hasModule
  };

}).call(this);
;}});
this.require.define({"common/init":function(exports, require, module){(function() {
  $(function() {
    var cmdHelper, requireAll;
    requireAll = require("common/commonjs_helper").requireByRegex;
    requireAll(/^common\/shims\//);
    requireAll(/^common\/extras\//);
    require("common/init");
    cmdHelper = require("common/commonjs_helper");
    return $(".js-comp").each(function() {
      var comp, path, ref;
      path = $(this).data("compPath") + "/view";
      if (cmdHelper.hasModule(path)) {
        comp = require(path);
        return (ref = comp.init) != null ? ref.call($(this)) : void 0;
      }
    });
  });

}).call(this);
;}});
this.require.define({"official/MyLove/index-head/view":function(exports, require, module){;}});
this.require.define({"official/MyLove/index/view":function(exports, require, module){(function() {
  module.exports = function() {
    return console.info("log from app!");
  };

}).call(this);
;}});
this.require.define({"official/MyLove/index/view":function(exports, require, module){module.exports = {
        init:function(){
                //alert("1");
        }
};


;}});
this.require.define({"app":function(exports, require, module){(function() {
  module.exports = function() {
    $("body").append(Handlebars.templates["welcome"]());
    return console.info("log from app!");
  };

}).call(this);
;}});
