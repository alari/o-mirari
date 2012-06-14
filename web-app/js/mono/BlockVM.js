(function() {
  var exports,
    __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; };

  exports = this;

  exports.BlockVM = (function() {

    function BlockVM() {
      this.fromJson = __bind(this.fromJson, this);      this.id = ko.observable("");
    }

    BlockVM.prototype.fromJson = function(json) {
      return this.id = ko.observable(json.id);
    };

    return BlockVM;

  })();

}).call(this);
