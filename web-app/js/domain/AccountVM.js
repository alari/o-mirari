(function() {
  var exports,
    __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; };

  exports = this;

  exports.AccountVM = (function() {

    function AccountVM() {
      this.fromJson = __bind(this.fromJson, this);      this.id = ko.observable(null);
      this.email = ko.observable(null);
      this.mainProfile = ko.observable(null);
    }

    AccountVM.prototype.fromJson = function(json) {
      var mapping;
      mapping = {
        mainProfile: {
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

    return AccountVM;

  })();

}).call(this);
