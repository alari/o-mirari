(function() {
  var exports,
    __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; };

  exports = this;

  exports.SecurityCodeVM = (function() {

    function SecurityCodeVM() {
      this.fromJson = __bind(this.fromJson, this);      this.id = ko.observable(null);
      this.account = ko.observable(null);
      this.dateCreated = ko.observable(null);
      this.token = ko.observable(null);
    }

    SecurityCodeVM.prototype.fromJson = function(json) {
      var mapping;
      mapping = {
        account: {
          create: function(o) {
            if (o.data) {
              return new AccountVM().fromJson(o.data);
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

    return SecurityCodeVM;

  })();

}).call(this);
