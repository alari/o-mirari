(function() {
  var exports,
    __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; };

  exports = this;

  exports.OwnerVM = (function() {

    function OwnerVM() {
      this.fromJson = __bind(this.fromJson, this);      this.id = "";
      this.displayName = "";
      this.avatar = new AvatarVM();
      this.url = "";
    }

    OwnerVM.prototype.fromJson = function(json) {
      this.id = json.id;
      this.displayName = json.displayName;
      this.avatar.fromJson(json.avatar);
      this.url = json.url;
      return this;
    };

    return OwnerVM;

  })();

}).call(this);
