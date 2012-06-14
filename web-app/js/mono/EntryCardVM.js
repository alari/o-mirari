(function() {
  var exports,
    __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; },
    __hasProp = Object.prototype.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor; child.__super__ = parent.prototype; return child; };

  exports = this;

  exports.EntryCardVM = (function() {

    __extends(EntryCardVM, CardVM);

    EntryCardVM.prototype.tmpl = "entry";

    function EntryCardVM() {
      this.fromJson = __bind(this.fromJson, this);      EntryCardVM.__super__.constructor.call(this);
      this.blocks = ko.observableArray([]);
      this.stacks = ko.observableArray([]);
    }

    EntryCardVM.prototype.fromJson = function(json) {
      var b, _i, _j, _len, _len2, _ref, _ref2;
      EntryCardVM.__super__.fromJson.call(this, json);
      _ref = json.blocks;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        b = _ref[_i];
        this.blocks.push(new BlockVM().fromJson(b));
      }
      _ref2 = json.stacks;
      for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
        b = _ref2[_j];
        this.stacks.push(new LinkVM().fromJson(b));
      }
      return this;
    };

    return EntryCardVM;

  })();

}).call(this);
