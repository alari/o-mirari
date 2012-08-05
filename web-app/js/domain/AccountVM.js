(function() {
  var exports,
    __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; },
    __hasProp = Object.prototype.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor; child.__super__ = parent.prototype; return child; };

  exports = this;

  exports.AccountVM = (function() {

    __extends(AccountVM, MyParent);

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
