(function() {
  var exports,
    __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; };

  exports = this;

  exports.mirari.data.BlocksHolderVM = (function() {

    function BlocksHolderVM() {
      this.fromJson = __bind(this.fromJson, this);      this.id = ko.observable(null);
      this.blocks = ko.observableArray([]);
      this.entry = ko.observable(null);
      this.id = ko.observable(null);
      this.version = ko.observable(null);
    }

    BlocksHolderVM.prototype.fromJson = function(json) {
      var mapping;
      mapping = {
        blocks: {
          create: function(o) {
            if (o.data) {
              return new mirari.data.BlockVM().fromJson(o.data);
            } else {
              return null;
            }
          },
          key: function(o) {
            return ko.utils.unwrapObservable(o.id);
          }
        },
        entry: {
          create: function(o) {
            if (o.data) {
              return new mirari.struct.EntryVM().fromJson(o.data);
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

    return BlocksHolderVM;

  })();

}).call(this);
