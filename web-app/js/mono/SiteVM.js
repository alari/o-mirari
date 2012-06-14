(function() {
  var exports,
    __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; };

  exports = this;

  exports.SiteVM = (function() {

    function SiteVM() {
      this.fromJson = __bind(this.fromJson, this);
      this.setCurrentN = __bind(this.setCurrentN, this);
      this.pushCard = __bind(this.pushCard, this);      this.cards = ko.observableArray([]);
      this.current = ko.observable(null);
      this.menu = ko.observableArray([]);
    }

    SiteVM.prototype.pushCard = function(card) {
      this.cards.push(card);
      if (!this.current()) return this.current(card);
    };

    SiteVM.prototype.setCurrentN = function(n) {};

    SiteVM.prototype.fromJson = function(json) {
      var m, _i, _len, _ref;
      this.pushCard(new PileCardVM().fromJson(json.root));
      _ref = json.menu;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        m = _ref[_i];
        this.menu.push(new LinkVM().fromJson(m));
      }
      return this;
    };

    return SiteVM;

  })();

}).call(this);
