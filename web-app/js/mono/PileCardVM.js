(function() {
  var exports,
    __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; },
    __hasProp = Object.prototype.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor; child.__super__ = parent.prototype; return child; };

  exports = this;

  exports.PileCardVM = (function() {

    __extends(PileCardVM, CardVM);

    PileCardVM.prototype.tmpl = "stack";

    function PileCardVM() {
      this.fromJson = __bind(this.fromJson, this);      PileCardVM.__super__.constructor.call(this);
      this.entries = ko.observableArray([]);
    }

    PileCardVM.prototype.fromJson = function(json) {
      var e, _i, _len, _ref;
      PileCardVM.__super__.fromJson.call(this, json);
      _ref = json.entries;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        e = _ref[_i];
        this.entries.push(new EntryCardVM().fromJson(e));
      }
      return this;
    };

    return PileCardVM;

  })();

}).call(this);
