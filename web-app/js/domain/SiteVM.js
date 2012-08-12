(function() {
  var exports,
    __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; };

  exports = this;

  exports.mirari.SiteVM = (function() {

    function SiteVM() {
      this.fromJson = __bind(this.fromJson, this);      this.id = ko.observable(null);
      this.account = ko.observable(null);
      this.dateCreated = ko.observable(null);
      this.displayName = ko.observable(null);
      this.host = ko.observable(null);
      this.id = ko.observable(null);
      this.kind = ko.observable(null);
      this.lastUpdated = ko.observable(null);
      this.name = ko.observable(null);
      this.portal = ko.observable(null);
      this.portalSite = ko.observable(null);
      this.profileSite = ko.observable(null);
      this.subSite = ko.observable(null);
      this.url = ko.observable(null);
      this.version = ko.observable(null);
    }

    SiteVM.prototype.fromJson = function(json) {
      var mapping;
      mapping = {
        account: {
          create: function(o) {
            if (o.data) {
              return new mirari.security.AccountVM().fromJson(o.data);
            } else {
              return null;
            }
          },
          key: function(o) {
            return ko.utils.unwrapObservable(o.id);
          }
        },
        portal: {
          create: function(o) {
            if (o.data) {
              return new mirari.SiteVM().fromJson(o.data);
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

    return SiteVM;

  })();

}).call(this);
