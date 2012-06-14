exports = this
class exports.SiteService
  @load: (vm)->
    jsonGetReact "/?load=1", (json)->
      vm.fromJson json.site