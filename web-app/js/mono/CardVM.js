(function() {
  var exports,
    __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; };

  exports = this;

  exports.CardVM = (function() {

    function CardVM() {
      this.fromJson = __bind(this.fromJson, this);      this.link = null;
      this.header = ko.observable("");
    }

    CardVM.prototype.fromJson = function(json) {
      this.header(json.header);
      this.link = new LinkVM().fromJson(json.link);
      return this;
    };

    return CardVM;

  })();

}).call(this);
