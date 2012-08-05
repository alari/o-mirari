(function() {
  var exports,
    __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; };

  exports = this;

  exports.PileVM = (function() {

    function PileVM() {
      this.fromJson = __bind(this.fromJson, this);      this.id = ko.observable(null);
      this.dateCreated = ko.observable(null);
      this.lastUpdated = ko.observable(null);
      this.name = ko.observable(null);
      this.site = ko.observable(null);
      this.title = ko.observable(null);
    }

    PileVM.prototype.fromJson = function(json) {
      var mapping;
      mapping = {
        site: {
          create: function(o) {
            if (o.data) {
              return new SiteVM().fromJson(o.data);
            } else {
              return null;
            }
          },
          key: function(o) {
            return ko.utils.unwrapObservable(o.id);
          }
        }
      };
      ko.mapping.fromJS(json, mapping, this);
      return this;
    };

    return PileVM;

  })();

}).call(this);
