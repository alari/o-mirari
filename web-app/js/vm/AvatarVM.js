(function() {
  var exports,
    __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; },
    __hasProp = Object.prototype.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor; child.__super__ = parent.prototype; return child; };

  exports = this;

  exports.AvatarVM = (function() {

    __extends(AvatarVM, ImageVM);

    function AvatarVM() {
      this.fromJson = __bind(this.fromJson, this);      AvatarVM.__super__.constructor.call(this);
      this.id = "";
      this.name = "";
      this.basic = true;
    }

    AvatarVM.prototype.fromJson = function(json) {
      AvatarVM.__super__.fromJson.call(this, json);
      this.id = json.id;
      this.name = json.name;
      this.basic = json.basic;
      return this;
    };

    return AvatarVM;

  })();

}).call(this);
