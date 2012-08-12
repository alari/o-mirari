(function() {
  var exports,
    __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; };

  exports = this;

  exports.mirari.data.BlockContentVM = (function() {

    function BlockContentVM() {
      this.fromJson = __bind(this.fromJson, this);      this.id = ko.observable(null);
      this.block = ko.observable(null);
      this.id = ko.observable(null);
      this.text = ko.observable(null);
      this.version = ko.observable(null);
    }

    BlockContentVM.prototype.fromJson = function(json) {
      var mapping;
      mapping = {
        block: {
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
        }
      };
      ko.mapping.fromJS(json, mapping, this);
      return this;
    };

    return BlockContentVM;

  })();

}).call(this);
