(function() {
  var exports,
    __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; };

  exports = this;

  exports.LinkVM = (function() {

    function LinkVM() {
      this.go = __bind(this.go, this);      this.code = ko.observable("");
      this.title = ko.observable("");
    }

    LinkVM.prototype.fromJson = function(json) {
      this.code(json.code);
      this.title(json.title);
      return this;
    };

    LinkVM.prototype.go = function() {
      return alert("Going to: " + this.code());
    };

    return LinkVM;

  })();

}).call(this);
