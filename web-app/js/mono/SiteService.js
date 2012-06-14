(function() {
  var exports;

  exports = this;

  exports.SiteService = (function() {

    function SiteService() {}

    SiteService.load = function(vm) {
      return jsonGetReact("/?load=1", function(json) {
        return vm.fromJson(json.site);
      });
    };

    return SiteService;

  })();

}).call(this);
