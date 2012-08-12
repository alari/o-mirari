(function() {
  var exports,
    __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; };

  exports = this;

  exports.mirari.data.BlockVM = (function() {

    function BlockVM() {
      this.fromJson = __bind(this.fromJson, this);      this.id = ko.observable(null);
      this.content = ko.observable(null);
      this.contentFile = ko.observable(null);
      this.contentPolicy = ko.observable(null);
      this.contentUrl = ko.observable(null);
      this.dateCreated = ko.observable(null);
      this.holder = ko.observable(null);
      this.id = ko.observable(null);
      this.lastUpdated = ko.observable(null);
      this.owner = ko.observable(null);
      this.title = ko.observable(null);
      this.type = ko.observable(null);
      this.version = ko.observable(null);
    }

    BlockVM.prototype.fromJson = function(json) {
      var mapping;
      mapping = {
        content: {
          create: function(o) {
            if (o.data) {
              return new mirari.data.BlockContentVM().fromJson(o.data);
            } else {
              return null;
            }
          },
          key: function(o) {
            return ko.utils.unwrapObservable(o.id);
          }
        },
        holder: {
          create: function(o) {
            if (o.data) {
              return new mirari.data.BlocksHolderVM().fromJson(o.data);
            } else {
              return null;
            }
          },
          key: function(o) {
            return ko.utils.unwrapObservable(o.id);
          }
        },
        owner: {
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

    return BlockVM;

  })();

}).call(this);
