$ ->
  requireAll = require("common/commonjs_helper").requireByRegex

  requireAll /^common\/shims\//
  requireAll /^common\/extras\//

  require "common/init"
  cmdHelper = require "common/commonjs_helper"

  $(".js-comp").each ->
    path = $(@).data("compPath") + "/view"
    if cmdHelper.hasModule path
      comp = require path
      comp.init?.call($(@))

